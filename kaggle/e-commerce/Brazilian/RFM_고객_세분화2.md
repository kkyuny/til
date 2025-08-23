RFM_고객_세분화2

### 1. RFM 점수대별 평균 리뷰 점수 확인
``` sql
WITH review AS (
SELECT  customer_unique_id
		,AVG(review_score) AS AVG_review_score
  FROM  olist_order_reviews_dataset AS r
  LEFT
  JOIN  olist_orders_dataset AS o
    ON  r.order_id = o.order_id
  LEFT
  JOIN  olist_customers_dataset AS c
    ON  o.customer_id = c.customer_id
 GROUP
    BY  customer_unique_id
)
SELECT  RFM_score
		,AVG(AVG_review_score) AS AVG_review_score
  FROM  RFM_LISTS_SCORE AS RFM
  LEFT
  JOIN  review AS review
    ON  RFM.customer_unique_id = review.customer_unique_id
 GROUP
    BY  RFM_score
 ORDER
    BY  RFM_score;
```
- customer_unique_id별 review_score를 구한다.
- 이 후, RFM_LISTS_SCORE와 customer_unique_id로 join 후 RFM_score로 그룹바이하여 리뷰 점수의 평균값을 집계한다.
- 실행결과
  - RFM_score별 점수의 분포가 고르게 있어 RFM_score와 리뷰 점수는 큰 관계가 없는 것 같다.

### 2. RFM 점수대별 평균 배송일 확인
``` sql
WITH delivery AS (
SELECT customer_unique_id
  , AVG(DATEDIFF(order_delivered_customer_date, order_purchase_timestamp)) as arrived_day
FROM olist_orders_dataset AS o
LEFT JOIN olist_customers_dataset AS c ON o.customer_id = c.customer_id
GROUP BY c.customer_unique_id
)
SELECT RFM_score
  , AVG(arrived_day) AS AVG_arrived_day
FROM RFM_LISTS_SCORE AS RFM
LEFT JOIN delivery AS d ON RFM.customer_unique_id = d.customer_unique_id
GROUP BY RFM_score
ORDER BY RFM_score;
```
- customer_unique_id별 평균 배송일을 구한 후 RFM_LISTS_SCORE와 조인 후 score별 그룹바이를 하여 평균 배송일을 집계한다.
- 실행결과
  - 스코어별 배송일이 고르게 분포되어 있어 점수별 평균 배송일은 큰 관계가 없는 것 같다.

### 3. 리뷰 점수대별 평균 배송일 확인
``` sql
SELECT  review_score
		, AVG(DATEDIFF(order_delivered_customer_date, order_purchase_timestamp)) as arrived_day
  FROM  olist_order_reviews_dataset AS r
  LEFT
  JOIN  olist_orders_dataset AS o
    ON  r.order_id = o.order_id
  LEFT
  JOIN  olist_customers_dataset AS c
    ON  o.customer_id = c.customer_id
 GROUP
    BY  review_score
 ORDER
    BY  review_score;
```
- 실행결과
	- 리뷰 점수가 높을 수록 평균 배송시간이 줄어든다.

## RFM 고객 세분화 분석
### 1. RFM 점수대별 분포가 종모양으로 정규분포를 따른다.
### 2. RFM 점수와 평균 리뷰 점수는 큰 관계가 없다.
### 3. RFM 점수와 평균 배송일은 큰 관계가 없다.
### 4. 평균 리뷰 점수가 높을 수록, 평균 배송일이 낮아지는 경향이 있다.
### 결론: RFM 점수는 고객 충성도 지표지만 리뷰 점수,배송일과는 상관성이 낮으므로 RFM이 높은 고객에 대해 리뷰,배송 지표를 개선하는 방향이 필요함.

### 4. 카테고리별 평균 배송일
``` sql
WITH DELIVERY_BY_product_category_name AS (
SELECT  o.order_id
		,pct.product_category_name_english
		,DATEDIFF(o.order_delivered_customer_date, o.order_purchase_timestamp) as arrived_day
  FROM  olist_orders_dataset AS o
  INNER 
  JOIN  olist_order_items_dataset AS oi 
    ON  o.order_id = oi.order_id
  LEFT 
  JOIN  olist_products_dataset AS p 
    ON  oi.product_id = p.product_id
  LEFT 
  JOIN  product_category_name_translation AS pct 
    ON  p.product_category_name = pct.product_category_name
)
SELECT  product_category_name_english
		,AVG(arrived_day) AS AVG_arrived_day
  FROM  DELIVERY_BY_product_category_name
 GROUP
    BY  1
 ORDER
    BY  2 ASC;
```
- 주문정보에서 배송일을 구한 후 해당 주문의 상품에 대한 카테고리명을 구한 후 카테고리명으로 group by하여 배송일의 평균을 집계하여 구한다.
- 실행결과
	- 카테고리별 배송일은 5~20일 정도의 분포로 나타난다.
	- 사실 정확한 계산은 배송일이 orders 테이블이 아니라 order_items 테이블에 있는 것이 정확하다.
	- 한 주문에서 여러개의 카테고리 상품을 구매할 수 있기 때문이다.
 	- 따라서 상품별 배송일자가 있어야지 정확한 카테고리별 배송일자를 구할 수 있다.
