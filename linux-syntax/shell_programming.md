## 쉘 프로그래밍을 하기 위해서는 반드시 확장자를 .sh로 생성해야한다.
nano script.sh // 파일이 없으면 파일을 생성한다.
chmod u+x script.sh

## 쉘 스크립트 실행
./script.sh

## if문1
if [ 1 -gt 2 ]; then
    echo "hello world1"
else
    echo "hello world2"
fi

## if문2
- 파일(-f), 디렉토리(-d) 존재여부 확인 가능
- 변수를 사용할 떄 ""와 $를 사용하는 것을 권장한다.
- 변수의 = 양 사이에는 공백이 없어야한다.
if [ -f "$file_name"]; then
    echo "$file_name file exists"
else
    echo "$file_name file does nit exist"
fi

## for문
for a in {1..100}
do
    echo "hello world$a"
done

## for문과 변수 활용
file_count=0
dir_count=0
other_count=0
for a in * // *: 현재 디렉토리를 순환한다.
do
    if [ -f :$a" ]; then
        let file_count=file_count+1
    elif [ -d "$a" ]; then
        let dir_count=dir_count+1
    else
        let other_count=other_count+1
    fi
done
echo "file_count is $file_count"
echo "dir_count is $file_count"
echo "other_count is $file_count"