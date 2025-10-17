- 문제
 
![image](https://github.com/user-attachments/assets/e5b353b5-6e9b-4c05-8ab7-38044e210634)

- 기존 풀이
```
import java.util.*;

class Solution {
    public List<Integer> solution(int[] num_list, int n) {             
        List<Integer> answer = new ArrayList<>();
        int i = 0;
        
        for(int num : num_list){
            if(i%n == 0) 
                answer.add(num);
            i++;
        }        
        return answer;
    }
}
```
 
![image](https://github.com/user-attachments/assets/44d8b26c-2fcb-46e2-a812-1c2b15529d2f)


- 리팩토링
```
import java.util.*;
import java.util.stream.*;

class Solution {
    public List<Integer> solution(int[] num_list, int n) {             
         return IntStream.range(0, num_list.length / n + (num_list.length % n == 0 ? 0 : 1))
                        .map(i -> num_list[i * n]) 
                        .boxed() 
                        .collect(Collectors.toList());
    }
}
```

![image](https://github.com/user-attachments/assets/591a07ea-e93c-4cd7-8429-91c3d0ed7a8d)

- til
  - 모던 자바(?)를 공부해보며 stream을 이용하여 코드를 리팩토링해보았다.
  - 하지만 실행속도가 100배 이상 늘어나는 결과를 얻었다.
  - 코드의 가독성은 좋아질 수 있지만 메서드 호출의 증가와 데이터 박싱으로 인해 실행속도가 늘어났다고 생각한다.
  - 그렇지만 실행속도가 100배나 느려지는건 이해하기가 어렵다. 나중에 ide에서도 실행해봐야겠다.
