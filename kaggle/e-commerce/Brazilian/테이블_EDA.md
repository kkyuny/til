## 테이블 확인
### 1. olist_order_items_dataset 확인
``` sql
SELECT * FROM olist_order_items_dataset LIMIT 10;
```
- 실행결과
  - product_id: 상품코드
  - seller_id: 판매자 ID
  - shipping_limit_date: 배송일자
  - price: 상품가격
  - freight_value: 화물가격(주문에 품목이 두 개 이상인 경우 화물가격은 품목 간 동일하게 분할된다.)
  - 주문한 내역의 품목 데이터가 저장된 테이블이다.

### 2. order_id가 2개 이상이고, 화물 가격이 2가지 이상인 경우 -> 없음
``` sql
SELECT order_id
    , product_id
    , count(order_id) as order_cnt
    , count(freight_value) as freight_value_cnt
    , count(distinct freight_value) as freight_value_distinct_cnt
FROM olist_order_items_dataset
GROUP BY order_id
    , product_id
HAVING count(order_id) > 1
LIMT 10;
```
- 실행결과
    - order_id 내에 여러 개의 product_id가 존재한다.     
    - order_cnt > 1일 때, product_id 별로 order_cnt와 freight_value_cnt의 값이 같고 freight_value_dinstinct_cnt는 1임.
    - 따라서, order_id의 product_id 별로 freight_value가 동일하게 분할된다.

### 3. 상품 배송 비용 확인
``` sql
SELECT  order_id
	,COUNT(order_id) AS order_id_cnt
	,SUM(price) AS SUM_price
	,SUM(freight_value) as SUM_freight_value
	,SUM(IFNULL(price,0)) + SUM(IFNULL(freight_value,0)) AS sale_amt
  FROM  olist_order_items_dataset
 WHERE  order_id = '005d9a5423d47281ac463a968b3936fb'
 GROUP
	BY  order_id;
```
- 실행결과
    - order_id 별 상품금액 합계와 배송비용 합계를 확인할 수 있다.
- 기타
    - IFNULL(A, B): A값이 NULL 이면 B 값을 반환

### 4. olist_order_items_dataset PK 확인
``` sql
SELECT  count(*) as cnt
	, COUNT(distinct CONCAT(order_id, order_item_id)) as order_id_order_item_id_distinct
	, count(order_id) as order_count_not_distinct
	, count(distinct order_id) as order_count
  FROM  olist_order_items_dataset;
```
- 실행결과
    - cnt = order_id_order_item_id_distinct = order_count_not_distinct != order_count 확인.
    - 전체 컬럼 개수와 특정 컬럼의 dictinct 개수와 동일하다면 해당 값을 pk로 볼 수 있다.
    - 해당 테이블은 동일 order_id가 있을 경우 order_item_id를 추가하여 pk를 관리한다.
    - 동일 order_id가 있는 경우: 여러 개의 품목(product_id)을 구매했을 때
    - 동일한 품목을 여러개 구매한 경우에도 각각의 order_item_id가 부여되며 freight_value가 동일하게 분할된다.
    - 즉, 해당 order의 각 product 1개 마다 pk가 부여된다.

### 5. olist_orders_dataset 확인
``` sql
SELECT * FROM olist_orders_dataset LIMIT 10;
```
- 실행결과
	- customer_id: 주문한 고객
	- order_status: 배송상태
  	- 배송예정일자, 배송일자 등등 주문에 대한 고객정보 및 배송정보가 저장된 테이블이다.

### 6. olist_orders_dataset PK
``` sql
SELECT count(*) as cnt
	, count(order_id) as order_cnt_not_distinct
	, count(distinct order_id) as order_cnt_distinct
FROM olist_orders_dataset
```
- 실행결과
  	- cnt = order_cnt_not_distinct = order_cnt_distinct 
	- order_id에 대한 count가 모두 동일하기 때문에 해당 테이블의 pk는 order_id이다.

### 7. product_category_name_translation 확인
``` sql
SELECT * FROM product_category_name_translation
```
- 실행결과
	- 브라질어인 카테고리 네임을 영어로 번역한 테이블이다.

### 8. olist_order_reviews_dataset
``` sql
SELECT * FROM olist_order_reviews_dataset LIMIT 10;
```
- 실행결과
	- review_id: 리뷰 ID
 	- order_id: 주문 ID
	- review_score: 리뷰 점수

### 9. olist_order_reviews_dataset PK
``` sql
SELECT count(*) cnt,
	count(distinct review_id) as, review_cnt
	count(review_id) as review_cnt_not_distinct
FROM olist_order_reviews_dataset;
```
- 실행결과
	- cnt(99223) != review_cnt(98409) != review_cnt_not_distinct(99223)
	- 중복된 review_id가 존재하기 때문에 review_id는 PK가 아니다.  

### 10. review_id가 2개 이상인 row 확인
``` sql
SELECT review_id
	, count(*) as cnt
FROM olist_order_reviews_dataset
GROUP BY review_id
HAVING cnt >= 2
LIMIT 1;
```
- 실행결과
	- cnt >= 2: review_id = '28642ce6250b94cc72bc85960aec6c62'

### 11. cnt >= 2 데이터 확인
``` sql
SELECT *
FROM olist_order_reviews_dataset
WHERE review_id = '28642ce6250b94cc72bc85960aec6c62';
```
- 실행결과
	- 해당 review_id는 2개의 order_id를 갖고 있다.
	- order_id = 'e239d280236cdd3c40cb2c033f681d1c','bc42a955f289870d5789e6e437206300'  
``` sql
SELECT *
FROM list_orders_dataset
WHERE order_id in ('e239d280236cdd3c40cb2c033f681d1c','bc42a955f289870d5789e6e437206300');
```
- 실행결과
	- 해당 order_id의 2개의 customer_id가 확인됌('e239d280236cdd3c40cb2c033f681d1c','bc42a955f289870d5789e6e437206300').
	- customer 테이블 데이터 확인도 필요함.
``` sql
SELECT *
FROM olist_customer_dataset
WHERE customer_id in ('e239d280236cdd3c40cb2c033f681d1c','bc42a955f289870d5789e6e437206300')
```
- 실행결과
	- 해당 customer_id들의 customer_unique_id는 동일함을 확인
- 추정결론
	- 상품 주문 시 orders_dataset 테이블에 order_id와 customer_id가 unique하게 부여된다.
	- 해당 customer_id는 reviews_dataset 테이블에 각 order별 입력될 수 있다.
	- 리뷰 작성 시 customers_dataset에 해당 customer_id가 입력된다(customer_unique_id는 N개의 리뷰를 작성할 수 있다.).
