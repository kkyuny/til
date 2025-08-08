## 테이블 확인2
### 1. 주문연월별 주문수 확인
``` sql
SELECT date_format(order_purchase_timestamp, '%Y-%m') as YM
  , count(distinct order_id) as order_cnt
FROM olist_orders_dataset
GROUP BY 1
ORDER BY 1;
```
- 실행결과
  - date_format으로 특정 date 추출 후 group by를 진행하면 특정 date의 집계현황을 확인할 수 있다.
  - 초기 row와 마지막 row 부근의 count수가 상대적으로 저조함. 
  - 정상적 주문은 2017-02~2018-08에 이루어진 것 같다.

### 2. 카테고리별 주문수 및 매출액 확인
``` sql
SELECT pct.product_category_name_english
  , count(distinct o.order_id) as order_distinct_cnt
  , SUM(IFNULL(oi.price, 0)) + SUM((IFNULL(oi.freight_value,0)) AS sale_amt
FROM olist_order_items_dataset AS oi
LEFT JOIN olist_orders_dataset AS o ON oi.order_id = o.order_id
LEFT JOIN olist_products_dataset AS p ON oi.product_id = p.product_id
LEFT JOIN product_category_name_translation AS pct ON  p.product_category_name = pct.product_category_name
GROUP BY 1
ORDER BY 2 DESC;
```
- 실행결과
  - bed_bath_table > health_beauty > sports_leisure > computers_accessories > furniture_decor..순의 주문수를 보여준다.
  - product_category_name_english에 null이 있다.(번역되지 않은 카테고리 값이 있다.)

### 3. 주문수가 가장 많은 지역 확인
``` sql
SELECT c.customer_city
  , COUNT(DISTINCT o.order_id) AS order_count
  , COUNT(DISTINCT c.customer_unique_id) AS customer_unique_count
FROM olist_orders_dataset AS o
LEFT JOIN olist_customers_dataset AS c ON o.customer_id = c.customer_id
GROUP BY c.customer_city
ORDER BY 2 DESC;
```
- 실행결과
  - sao paulo가 order_count(15540건), customer_unique_count(14984건)으로 가장 많다.
  - order_id와 customer_unique_id의 수가 차이나는 것은 N번 구매한 구매자가 있기 때문이다.

### 4. N번 구매한 구매자 수 확인
``` sql
SELECT COUNT(order_cnt),
  CASE WHEN order_cnt >= 5 THEN '5_Order_Over'
    WHEN order_cnt >= 4 THEN '4_Order'
    WHEN order_cnt >= 3 THEN '3_Order'
    WHEN order_cnt >= 2 THEN '2_Order'
    ELSE '1_Order'
  END AS order_segment
FROM (
  SELECT c.customer_unique_id
    , COUNT(DISTINCT o.order_id) AS order_cnt
  FROM olist_orders_dataset AS o
  LEFT JOIN olist_customers_dataset AS c ON o.customer_id = c.customer_id
  GROUP BY c.customer_unique_id
) AS A
GROUP BY order_segment;
```
- 실행결과
  - 1번 구매(93099건), 2번 구매(2745건), 3번 구매(203건), 4번 구매(30건), 5회 이상 구매(19건) 확인
  - 2회 이상 구매 고객이 거의 없음.

### 5. 함께 자주 구매하는 카테고리 확인
- 주문 및 상품 정보 관련 임시 테이블 생성
``` sql
CREATE TEMPORARY TABLE ORDER_PRODUCT_INFO_LISTS AS (
SELECT  o.order_id
		,oi.order_item_id
        ,pct.product_category_name_english
  FROM  olist_orders_dataset AS o
  LEFT 
  JOIN  olist_order_items_dataset AS oi 
    ON  o.order_id = oi.order_id
  LEFT 
  JOIN  olist_products_dataset AS p 
    ON  oi.product_id = p.product_id
  LEFT 
  JOIN  product_category_name_translation AS pct 
    ON  p.product_category_name = pct.product_category_name  
);
```

- order_id별 구매한 category 확인
``` sql
SELECT  order_id 
		, GROUP_CONCAT(DISTINCT product_category_name_english ORDER BY product_category_name_english ASC) AS product_cates
  FROM  ORDER_PRODUCT_INFO_LISTS
 GROUP
	BY  1
HAVING COUNT(DISTINCT product_category_name_english) > 1;
```
- 실행결과
  - 2개 이상의 상품을 주문한 order_id에서 중복을 제거한 구매한 카테고리를 ,로 연결하여 출력한다.
  - product_cates: consoles_games, toys 등등
  - product_cates를 통해 함께 자주 구매한 카테고리들을 확인할 수 있다.
    
- product_cates별 주문건수 확인
``` sql
WITH ORDER_PRODUCT_INFO_LISTS_GROUP AS (
SELECT  order_id 
		,GROUP_CONCAT(DISTINCT product_category_name_english ORDER BY product_category_name_english ASC) AS product_cates
  FROM  ORDER_PRODUCT_INFO_LISTS
 GROUP
	BY  1
)
SELECT  product_cates
		,COUNT(DISTINCT order_id) AS order_count
  FROM  ORDER_PRODUCT_INFO_LISTS_GROUP
 WHERE  product_cates LIKE '%,%'
 GROUP
    BY  1
 ORDER
    BY  2 DESC;
```
- order_id별 구매한 categorys를 확인한 쿼리문을 with문으로하여
- product_cates로 그룹화를 한 뒤 중복을 제거한 order_id를 count하면 product_cates별 주문 건수를 확인할 수 있다.

### 6. olist_orders_dataset 및 olist_order_items_dataset 데이터 손실 여부 확인
``` sql
SELECT  *
  FROM  olist_orders_dataset AS o
  LEFT 
  JOIN  olist_order_items_dataset AS oi 
    ON  o.order_id = oi.order_id
 WHERE  oi.order_id IS NULL
 LIMIT  10;
```
- 실행결과
	- oi의 데이터가 null이 있는 데이터를 확인하였음.

``` sql
SELECT  COUNT(distinct o.order_id) as order_cnt
	,COUNT(distinct CASE WHEN oi.order_id IS NULL THEN o.order_id) as not_match_order_cnt
	,MAX(DISTINCT CASE WHEN oi.order_id IS NULL THEN o.order_id END) AS not_match_order_id_sample
  FROM  olist_orders_dataset AS o
  LEFT 
  JOIN  olist_order_items_dataset AS oi 
    ON  o.order_id = oi.order_id
```
- 실행결과
	- order_cnt(99441), not_match_order_cnt(775), not_match_order_id_sample(ff71fa43cf5b726cd4a5763c7d819a35)
	- 주문 데이터(775건)의 아이템 내역이 유실되었다.

``` sql
SELECT  *
  FROM  olist_orders_dataset
 WHERE  order_id = 'ff71fa43cf5b726cd4a5763c7d819a35';
 
SELECT  *
  FROM  olist_order_items_dataset
 WHERE  order_id = 'ff71fa43cf5b726cd4a5763c7d819a35';
```
- 실행결과
	- orders의 데이터는 있지만 order_items 데이터는 없다.

### 7. 평균 배송시간이 가장 느린 카테고리 확인
``` sql
WITH DELIVERY_BY_PRODUCT AS (
  SELECT 
    p.product_id,
    pct.product_category_name_english,
    DATEDIFF(o.order_delivered_customer_date, o.order_purchase_timestamp) AS arrived_day
  FROM olist_orders_dataset AS o
  INNER JOIN olist_order_items_dataset AS oi ON o.order_id = oi.order_id
  LEFT JOIN olist_products_dataset AS p ON oi.product_id = p.product_id
  LEFT JOIN product_category_name_translation AS pct ON p.product_category_name = pct.product_category_name
  GROUP BY p.product_id, pct.product_category_name_english, o.order_delivered_customer_date, o.order_purchase_timestamp
)
SELECT 
  product_category_name_english,
  AVG(arrived_day) AS avg_arrived_day
FROM DELIVERY_BY_PRODUCT
GROUP BY product_category_name_english
ORDER BY avg_arrived_day DESC;  
```
- 실행결과
	- order의 상품별 배송기간 계산(GROUP BY 처리 필요)
	- 카테고리 이름별 그룹 바이 후 평균 배송시간 계산
	- 카테고리별 배송기간의 차이가 많다.(5~20일 분포)

### 8. 평균 배송시간이 가장 느린 도시 확인
``` sql
WITH DELIVERY_BY_customer_city AS (
SELECT  c.customer_city
		,DATEDIFF(order_delivered_customer_date, order_purchase_timestamp) as arrived_day
  FROM  olist_orders_dataset AS o
  INNER 
  JOIN  olist_order_items_dataset AS oi 
    ON  o.order_id = oi.order_id
  LEFT 
  JOIN  olist_customers_dataset AS c 
    ON  c.customer_id = o.customer_id    
)
SELECT  customer_city
		,AVG(arrived_day) AS AVG_arrived_day
  FROM  DELIVERY_BY_customer_city
 WHERE  arrived_day IS NOT NULL
 GROUP
    BY  1
 ORDER
    BY  2 DESC; 
```
- 실행결과
	- 배송기간이 3~148일로 격차가 크다.
- 도출방법
	- 상품의 배송기간을 DATEDIFF로 구한 후 도시별로 그룹바이 후 배송기간의 평균을 집계하여 값을 구할 수 있다.

### 9. 도시별 셀러당 고객 분포 수 확인
``` sql
WITH customer_cnt AS (
SELECT  customer_state
		,COUNT(DISTINCT customer_unique_id) AS customer_cnt
  FROM  olist_customers_dataset
 GROUP
    BY  1
),
seller_cnt AS (
SELECT  seller_state
        ,COUNT(DISTINCT seller_id) AS seller_cnt
  FROM  olist_sellers_dataset
 GROUP
    BY  1
)
SELECT  c.customer_state
		,customer_cnt
        ,seller_cnt
        ,customer_cnt / seller_cnt AS number_customers_per_seller
  FROM  customer_cnt AS c 
  LEFT
  JOIN  seller_cnt AS s
    ON  c.customer_state = s.seller_state
 ORDER
    BY  4 DESC;
```
- 실행결과
	- 고객 수가 적은 지역의 셀러의 수는 적고, 고객 수가 많은 지역일 수록 셀러의 수가 많다.
	- 셀러 당 고객수가 가장 많은 지역들은 비교적 고객 수가 적은 지역이다.
- 도출방법
	- 지역별 고객 수 카운트 테이블과 지역별 셀러 수 카운트 테이블을 구한다.
	- 두 테이블을 지역을 기준으로 left join 후 고객 수 카운트 / 셀러 수 카운트를 통하여 결과를 구한다.

### 10. 지역별 평균 배송 시간 확인
``` sql
WITH DELIVERY_BY_customer_state AS (
SELECT  c.customer_state
		,DATEDIFF(order_delivered_customer_date, order_purchase_timestamp) as arrived_day
  FROM  olist_orders_dataset AS o
  INNER 
  JOIN  olist_order_items_dataset AS oi 
    ON  o.order_id = oi.order_id
  LEFT 
  JOIN  olist_customers_dataset AS c 
    ON  c.customer_id = o.customer_id    
)
SELECT  customer_state
		,AVG(arrived_day) AS AVG_arrived_day
  FROM  DELIVERY_BY_customer_state
 WHERE  arrived_day IS NOT NULL
 GROUP
    BY  1
 ORDER
    BY  2 DESC; 
```
- 실행결과
	- 지역별 배송기간이 8~28일로 차이가 많이 난다.
- 도출방법
	- 지역별 배송기간 테이블을 DATEDIFF를 이용하여 구한다.
	- 지역별 그룹바이 후 배송기간의 평균값을 집계하여 결과를 구한다. 
