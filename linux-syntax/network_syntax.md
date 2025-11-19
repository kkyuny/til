## 모든 프로세스 목록 조회
- ps -ef: e는 모든 프로세스, f는 full format
- ps -ef | grep -rni "찾을 프로세스 이름"

## 프로그램 강제 종료
- kill -9 프로세스 ID(PID)

## 패키지 관련
- yum: 레드헷 계열 패키지 관리 도구
- apt, apt-get: 데비안계열 패키지 관리 도구
- sudo apt-get update: 패키지 목록 최신화
- sudo apt-get upgrade: 패키지 버전 최신화

## nginx 설치/실행/정지
- sudo apt-get install nginx
- sudo systemctl start nginx: systemctl -> 프로그램 실행관리 도구
- sudo systemctl stop nginx

## ip 정보 조회
- ifconfig: 나의 ip정보 조회
- nslookup naver.com: 특정 도메인의 ip주소 정보 조회(DNS서버에 문의)

## 네트워크 연결상태 조회(ip)
- ping ip주소

## 네트워크 연결상태 조회(ip + port)
- nc -zv ip또는도메인 port번호: ex)nc -zv naver.com 443
- telnet 도메인 port번호: telnet은 실제 데이터까지 전송하여 확인하므로, 차단되거나 권장되지 않는다.

## 원격접속(22번 port 사용)
- ssh username@host주소(ip 또는 도메인)

## 원격파일 전송
-scp 전송하고자하는파일의위치 원격지주소
