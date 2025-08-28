## RFM 고객 세분화 및 지역별 배송 분석
### 1. RFM 점수대별 회원수 및 특정지역 회원수
``` sql
SELECT  RFM_SCORE
		,COUNT(DISTINCT RFM.customer_unique_id) AS CUSTOMER_CNT
    ,COUNT(DISTINCT DELIVERY.customer_unique_id) AS CUSTOMER_CNT_SEG_Y
    ,COUNT(DISTINCT DELIVERY.customer_unique_id)/COUNT(DISTINCT RFM.customer_unique_id) AS CUSTOMER_CNT_SEG_Y_RATIO
  FROM  RFM_LISTS_SCORE AS RFM
  LEFT
  JOIN  DELIVERY_BY_STATE_SEG_Y AS DELIVERY
    ON  RFM.customer_unique_id = DELIVERY.customer_unique_id
 GROUP
    BY  1
 ORDER
    BY  1;
```
- 실행결과
  - RFM 스코어별 고객수는 정규분포모양을 띈다.
  - RFM 스코어가 높아질수록 SEG_Y 고객(셀러 당 할당 고객이 많은 지역) 비율이 높아진다.(RFM 3(0.03) -> RFM11(0.07))

### 2. RFM 점수대별 특정지역 회원 매출 기여도
``` sql
SELECT  RFM_SCORE
		,SUM(monetary) AS monetary
    ,SUM(CASE WHEN DELIVERY.customer_unique_id IS NOT NULL THEN monetary END) AS monetary_SEG_Y
    ,ROUND(SUM(CASE WHEN DELIVERY.customer_unique_id IS NOT NULL THEN monetary END)/SUM(monetary)*100,2) AS monetary_SEG_Y_RATIO
  FROM  RFM_LISTS_SCORE AS RFM
  LEFT
  JOIN  DELIVERY_BY_STATE_SEG_Y AS DELIVERY
    ON  RFM.customer_unique_id = DELIVERY.customer_unique_id
 GROUP
    BY  1
 ORDER
    BY  1;
```
- 실행결과
  - RFM 스코어별 구매금액은 정규분포모양을 띈다.
  - RFM 스코어가 높아질수록 SEG_Y 고객(셀러 당 할당 고객이 많은 지역)의 구매금액 비율이 높아진다.(RFM 3(3.56%) -> RFM11(6.84%))

### 3. RFM 점수대별 평균 리뷰점수
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
        ,AVG(CASE WHEN DELIVERY.customer_unique_id IS NOT NULL THEN AVG_review_score END) AS AVG_review_score_SEG_Y
  FROM  RFM_LISTS_SCORE AS RFM
  LEFT
  JOIN  DELIVERY_BY_STATE_SEG_Y AS DELIVERY
    ON  RFM.customer_unique_id = DELIVERY.customer_unique_id
  LEFT
  JOIN  review AS review
    ON  RFM.customer_unique_id = review.customer_unique_id
 GROUP
    BY  RFM_score
 ORDER
    BY  RFM_score;
```
- 고객별 리뷰점수를 구한 후 customer_unique_id로 group by 한 뒤 RFM_LISTS_SCORE와 join하여 데이터를 구한다.
- 실행결과
  - 전체지역의 RFM 스코어별 리뷰 점수는 별다른 특징이 없다.
  - 하지만 SEG_Y 지역 고객의 RFM 스코어는 높아질수록 리뷰점수도 함께 높아진다.
 
### 4. RFM 점수대별 평균 배송일자
``` sql
SELECT RFM_score
	,AVG(DATEDIFF(o.order_delivered_customer_date, o.order_purchase_timestamp)) as arrived_day
FROM DELIVERY_BY_STATE AS DBS
LEFT JOIN olist_orders_dataset AS o ON DBS.order_id = o.order_id
LEFT JOIN RFM_LISTS_SCORE AS RLS ON RLS.customer_unique_id = DBS.customer_unique_id
GROUP BY RFM_score
ORDER BY RFM_score;
```
- 실행결과
  - RFM 점수대별 배송일은 점수가 높아질수록 높아지다가 RFM 10점부터 급격하게 낮아지는 경항이 있다.

### RFM 고객 세분화 및 지역별 배송 분석 결론
-- 1. RFM 점수대가 높을 수록, SEG_Y 지역의 회원수, 매출 비중, 리뷰 점수가 높아진다.
-- 2. 하지만, RFM 8~9점대의 비교적 충성고객의 배송일은 하위 RFM 고객보다 높았다.
-- 결론: SEG_Y 지역의 8~9점대 고객에게 배송일이 빠른 상품 추천 등을 통하여 배송일을 낮추는 관리가 필요해보인다. 
