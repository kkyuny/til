## 사용자 추가
- useradd 유저이름 
- sudo useradd 유저이름: 권한 관련 에러 발생시
- sudo useradd -m 유저명: 유저 + 홈디렉토리 자동 생성 

## 비밀번호 지정
- passwd 유저이름

## 사용자/그룹 목록 조회
- cat /ect/passwd
- cat /ect/group

## 로그인 사용자 변경: 변경하고자 하는 계정의 비밀번호를 입력한다.
## sudo는 현재 사용자 계정의 비밀번호를 입력
- su - 변경할사용자계정
- exit: 원래 계정으로 복귀

## 사용자 권한 변경
- chmod rwx 파일명: r(4), w(2), x(1)
- chmod u+x 파일명: user의 읽기 권한 추가

## 소유자, 그룹 변경
- chown 소유자명:그룹명 파일명

## 그룹에 유저 추가
- sudo usermod -aG 그룹명 유저명: 해당 유저를 그룹에 추가
- sudo usermod -G 그룹명 유저명: 해당 유저의 기존 그룹을 모두 삭제 후 추가

## 유저가 속한 그룹 확인
- groups 유저이름

## 그룹 생성/삭제
- sudo groupadd 그룹명
- sudo groupadel 그룹명
