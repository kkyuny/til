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
- 실행결과
  - customers 테이블의 고객 unique id를 기준으로 그룹바이를 진행(order table과 customer_id로 조인)
  - RECENCY: 고객별 (기준날자 - 가장 최근 구매일자)의 DATEDIFF를 통해 계산
  - FREQUENCY: 고객의 총 주문건수 COUNT
  - MONETARY: order_items 테이블의 price와 freight_value의 sum을 통해 계산
