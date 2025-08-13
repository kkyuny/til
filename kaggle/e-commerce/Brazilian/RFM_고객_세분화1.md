## RFM(Recency, Frequency, Monetary) 분석 테이블 생성
- RFM: RECENCY(최근) / FRQUENCY(빈도) / MONETARY(금액)

### 1. RECENCY 기준일자 확인
``` sql
SELECT  MAX(order_purchase_timestamp) as MAX_order_purchase_timestamp
FROM  olist_orders_dataset;
```
- 실행결과
  - 2018-10-17 17:30:18

### 2. RFM_LISTS 테이블 생성  
``` sql
CREATE TABLE RFM_LISTS AS (
SELECT  c.customer_unique_id
		,DATEDIFF('2018-10-17', MAX(order_purchase_timestamp)) AS recency -- 2018-10-17 기준일자
        ,COUNT(distinct o.order_id) AS frequency
        ,SUM(IFNULL(oi.price,0)) + SUM(IFNULL(oi.freight_value,0)) AS monetary -- * NULL + 1 = NULL
  FROM  olist_orders_dataset AS o
  INNER 
  JOIN  olist_order_items_dataset AS oi 
    ON  o.order_id = oi.order_id
  LEFT
  JOIN  olist_customers_dataset AS c
    ON  o.customer_id = c.customer_id
 GROUP
    BY  1
);
```
- 실행결과
  - customers 테이블의 고객 unique id를 기준으로 그룹바이를 진행(order table과 customer_id로 조인)
  - RECENCY: 고객별 (기준날자 - 가장 최근 구매일자)의 DATEDIFF를 통해 계산
  - FREQUENCY: 고객의 총 주문건수 COUNT
  - MONETARY: order_items 테이블의 price와 freight_value의 sum을 통해 계산

### 3. RFM_LISTS 세그먼트 5분위 데이터 확인
``` sql
SELECT  *
		,NTILE(5) OVER (ORDER BY recency DESC) AS recency_segment
        ,NTILE(5) OVER (ORDER BY frequency ASC) AS frequency_segment
        ,NTILE(5) OVER (ORDER BY monetary ASC) AS monetary_segment
  FROM  RFM_LISTS; 
```
- NTILE: 윈도우 함수로, 데이터를 N개의 동일한 크기(또는 최대한 비슷한 크기)로 나누고, 각 행에 그룹 번호를 할당하는 함수.
- 실행결과
	- RFM_LISTS 데이터에 RFM별로 분위값이 생성되어 출력된다.
	- 특이점으로 frequency는 5분위를 제외하고 min과 max의 값이 1이다.(1~4분위의 고객은 모두 1번만 구매를 했다.) 

### 4. RFM_LISTS 세그먼트 5분위 테이블 생성
``` sql
CREATE TABLE RFM_LISTS_SEGMENT AS (
WITH NTILE_BASE AS (
SELECT  *
		,NTILE(5) OVER (ORDER BY recency DESC) AS recency_segment
        ,NTILE(5) OVER (ORDER BY frequency ASC) AS frequency_segment
        ,NTILE(5) OVER (ORDER BY monetary ASC) AS monetary_segment
  FROM  RFM_LISTS
),
recency_segment AS (
SELECT  'recency' AS segment
		,recency_segment as segment_value
		,MIN(recency) AS MIN_value
        ,MAX(recency) AS MAX_value
        ,COUNT(DISTINCT customer_unique_id) AS customer_cnt_recency
  FROM  NTILE_BASE
 GROUP
    BY  1,2
),
frequency_segment AS (
SELECT  'frequency' AS segment
		,frequency_segment as segment_value
		,MIN(frequency) AS MIN_value
        ,MAX(frequency) AS MAX_value
        ,COUNT(DISTINCT customer_unique_id) AS customer_cnt_frequency
  FROM  NTILE_BASE
 GROUP
    BY  1,2
),
monetary_segment AS (
SELECT  'monetary' AS segment
		,monetary_segment as segment_value
		,MIN(monetary) AS MIN_value
        ,MAX(monetary) AS MAX_value
        ,COUNT(DISTINCT customer_unique_id) AS customer_cnt_monetary      
  FROM  NTILE_BASE
 GROUP
    BY  1,2
)
SELECT  *
  FROM  recency_segment
 UNION  ALL
SELECT  *
  FROM  frequency_segment
 UNION  ALL
SELECT  *
  FROM  monetary_segment
);
```
- 세그먼트 5분위로 나눈 테이블에서 각 RFM의 segment_value별 그룹바이를 진행 후 각각의 min, max, count(customer_unique_id)를 집계

# 5. RFM 고객 세분화 테이블 생성
``` sql
CREATE TABLE RFM_LISTS_SCORE AS (
WITH BASE AS (
SELECT  *
		,CASE WHEN recency >= 433 THEN 1
              WHEN recency >= 317 THEN 2
              WHEN recency >= 226 THEN 3
              WHEN recency >= 142 THEN 4
              ELSE 5
              END AS recency_score
		,1 AS frequency_score
        ,CASE WHEN monetary >= 209 THEN 5
              WHEN monetary >= 133 THEN 4
              WHEN monetary >= 87 THEN 3
              WHEN monetary >= 55 THEN 2
              ELSE 1
              END AS monetary_score
  FROM  RFM_LISTS
)
SELECT  *
		,recency_score + frequency_score + monetary_score as RFM_score
  FROM  BASE
);
```
- 고객별 RFM score를 합산한 테이블 생성

# 6. RFM SOCRE별 고객 수 확인
``` sql
SELECT RFM_score
	, COUNT(disticnt customer_unique_id) AS customer_unique_id_cnt
FROM RFM_LISTS
GROUP BY RRM_score;
```
- 실행결과
	- 3(3917), 4(7639), 5(11908), 6(14652), 7(18741), 8(15390), 9(11484), 10(7796), 11(3893)
