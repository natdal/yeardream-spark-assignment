// TODO : 흠... 인텔리J에서 인풋 입력이 안받아지네... 어디 설정이 잘못된걸까....

/*
3.9.2 퀴즈 2
다음 주어진 List 에 있는 단어의 수와, 총 문자의 수를 한번에 계산하는 함수를 collection 을 이용해서 계산하세요.
```
val wordList = List("apple", "basket", "candy") // 5, 6, 5
```
*/
//import scala.io.StdIn._;
//
//println("입력 없이 Enter 치면 기본값 적용");
//println("default : \"apple\", \"basket\", \"candy\")");
//println("단어 입력 (쉼표로 구분) : ");
//val wordsInput = readLine().trim;
//val wordsList =  if (wordsInput.isEmpty) {
//  List("apple", "basket", "candy")
//}else{
//  wordsInput.split(",").map(_.trim).toList
//  // "a,b , c" -> split -> Array("a","b "," c")
//  // -> map -> 모든 요소 돌아댕기면서 인자로 받은 함수 적용 후
//  // 새로운 컬렉션 반환
//  // -> _.time -> 앞 뒤 공백 날리기 ->
//  // -> toList -> Array("a", "b", "c") -> List("a","b","c")
//};

val wordList = List("apple", "basket", "candy") // 5, 6, 5

val count = wordList.map(_.length);
val total = count.sum;
println("3.9.2 퀴즈 2");
println("ANSWER :");
println("단어몇개? ", wordList.length);
println("글자몇개? ", total);

// 일단 방향성은 잡았음. collection이라는 힌트가 있으니 글자수랑 인덱스 순회하는게 있을거임.
// forleft : 왼쪽에서 오른쪽으로 한칸씩 이동하면서 주어진 함수를 적용하면서 값을 누적(fold)시킴.
// 첫번째 인자 : 현재값. 두번째 인자 : 누적시킬 값. 즉, 누적값에 튜플을 넘겨줌으로써 반환값도 튜플로 받을 수 있게 됨.
// 왜 그러한가? acc = z 라는 초기값이 있기 때문임. foldLeft에서 acc를 반환값으로 정의해놨음. 그리고 B라는 이름의 제네릭 타입으로 보임.
// 즉 3번만 돌면 끝.
val (wordCount, charCount) = wordList.foldLeft((0,0)) { // scala 특성. wordlist.컬렉션을 하면 앞부분을 첫번째 인자로 해석.
  case ((wc, cc), word) => (wc + 1, cc + word.length) // (op: (B, A) => B) 즉 B의 값을 뽑아낼 수 있다.
};
println(s"단어몇개? $wordCount, 글자몇개? $charCount");
/*
override /*TraversableLike*/
def foldLeft[B](z: B)(@deprecatedName('f) op: (B, A) => B): B = {
  var acc = z // z가 (0,0)임
  var these = this // this에 wordlist가 들어가는거임
  while (!these.isEmpty) {
    acc = op(acc, these.head)
    these = these.tail
  }
  acc
}
*/

// 일단 문제는 풀었으니 더 간단한 방법으로 ㄱㄱ
var wordCount1 = 0
var charCount1 = 0
for (word <- wordList) {
  wordCount1 += 1
  charCount1 += word.length // string은 배열이니까
}
println(s"단어몇개? $wordCount1, 글자몇개? $charCount1");

/*
6.3.1 퀴즈 1
반복문을 이용해서 Fibonnaci 수열을 담은 Array를 만들어 보세요.
def fibonnaci(size: Int) = Array[Int]
*/
def Fibonacci(size: Int): Array[BigInt] = {
  if (size <= 0) return Array(); // 빈 배열 반환

  val result = new Array[BigInt](size); // size만큼의 크기의 배열 생성
  result(0) = 0; // index 0 = 0
  if (size > 1) result(1) = 1; // size가 1 이상일시 index 1 = 1

  for (i <- 2 until size) { // 0 , 1 빼고 index 2부터 시작
    result(i) = result(i - 1) + result(i - 2);
  }

  result;
}

// 중간에 마이너스가 뜨네? BigInt로 바꿔봄
// 어차피 값이 무한하지 않음.
// 표기법을 바꿔봄
println(Fibonacci(50).mkString(", "));

/*
6.3.2 퀴즈 2
2차원 행렬을 나타내는 타입 Matrix 를 선언하고, Matrix의 곱셈을 표현하는 operator 를 정의해보세요.
  - hint : alias 에 대한 생성자
    ```
    type Row = Array[Int]
    Row(1,1) // fail

    def Row(xs: Int*) = Array(xs: _*) // alias type 에 대한 생성자역할을 하는 함수

    Row(1,1)
    ```
    alias type 에 대한 operator overloading - implicit class 를 이용 (scala 2.x )
    ```
    implicit class MatrixOperators(_this: Matrix) {
      def *(other: Matrix): Option[Matrix] = {
        // your implementaion
      }
    }
    ```
*/
type Row = Array[Int] // 열
def Row(x: Int*): Row = Array(x: _*) // 곱연산 정의

type Matrix = Array[Row]
def Matrix(x: Row*): Matrix = Array(x: _*)

// 못품. 내일 아침에 풀기.
//implicit class MatrixOperators(_this: Matrix) {
//  def *(other: Matrix): Option[Matrix] = {
//
//  }
//}

// case 우찌쓰드라.
