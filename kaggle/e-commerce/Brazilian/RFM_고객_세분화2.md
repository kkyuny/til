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

### 3. 
