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
  - bed_bath_table > health_beauty > sports_leisure > computers_accessories > furniture_decor..
  - 순의 주문수를 보여준다.
  - product_category_name_english에 null이 있다.(번역되지 않은 카테고리 값이 있다.)     
