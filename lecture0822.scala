package org.yeardream.scala

/*
1. 사람은 자식, 부모님, 조부모님이 있다.
  - 사람 : Person
    - 자식 : Child
    - 부모님 : Parent
    - 조부모님 : Grandparent
2. 모든 사람은 이름, 나이, 현재 장소정보(x,y좌표)가 있다.
Person {
  name string
  age int
  location x,y // 이건 따로 빼는게 좋지 않나? 사람 외에 다른 것도 이동할 수 있으니까.
}
3. 모든 사람은 걸을 수 있다. 장소(x, y좌표)로 이동한다.
Person {
  ...
  speed Int

  move(translateX, translateY): Unit = {
    moveExecute(translateX * speed, translateY * speed)
  }
}
4. 자식과 부모님은 뛸 수 있다. 장소(x, y좌표)로 이동한다.
Runner : Person {
  override move(...){
    super()
  }

  run(translateX, translateY) : Unit = {
    speed = speed + runSpeed
    moveExecute(translateX * speed, translateY * speed)
  }
}
Child : Runner
Parent : Runner
5. 조부모님의 기본속도는 1이다. 부모의 기본속도는 3, 자식의 기본속도는 5이다.
Grandparent : Person {
  override speed = 1
}
Parent : Person {
  override speed = 3
}
Child : Person {
  override speed = 5
}
6. 뛸때는 속도가 기본속도대비 +2 빠르다.
Runner : Person {
  runSpeed = 2
  override move(...){
    speed += runSpeed
  }

  run(translateX, translateY) : Unit = {
    // 아.. 지문 특성상 speed 곱하는것보다 +로 하는게 더 나을듯.
    moveExecute(translateX + runSpeed, translateY + runSpeed)
  }
}
7. 수영할때는 속도가 기본속도대비 +1 빠르다.
Swimmer : Person {
  swimSpeed = 1
  override move(...){
    speed += swimSpeed
  }
}
8. 자식만 수영을 할 수 있다. 장소(x, y좌표)로 이동한다.
Child : Swimmer
*/
/*
위 요구사항을 만족하는 클래스들을 바탕으로, Main 함수를 다음 동작을 출력(`System.out.println`)하며 실행하도록 작성하시오. 이동하는 동작은 각자 순서가 맞아야 합니다.

1. 모든 종류의 사람의 인스턴스는 1개씩 생성한다.
2. 모든 사람의 처음 위치는 x,y 좌표가 (0,0)이다.
3. 모든 사람의 이름, 나이, 속도, 현재위치를 확인한다.
4. 걸을 수 있는 모든 사람이 (1, 1) 위치로 걷는다.
5. 뛸 수 있는 모든 사람은 (2,2) 위치로 뛰어간다.
6. 수영할 수 있는 모든 사람은 (3, -1)위치로 수영해서 간다.
*/

case class Location(x: Int, y: Int) {
  def translate(dx: Int, dy: Int): Location = Location(x + dx, y + dy)
}

abstract class Person(val name: String, val age: Int, var location: Location, val speed: Int) {
  def move(translateX: Int, translateY: Int): Unit = {
    location = location.translate(translateX + speed, translateY + speed)
  }
}

trait Runner extends Person {
  val runSpeed: Int = 2
  override def move(translateX: Int, translateY: Int): Unit = {
    location = location.translate(translateX + speed + runSpeed, translateY + speed + runSpeed)
  }

  def run(translateX: Int, translateY: Int): Unit = {
    move(translateX, translateY)
  }
}

trait Swimmer extends Person {
  val swimSpeed: Int = 1
  override def move(translateX: Int, translateY: Int): Unit = {
    location = location.translate(translateX + speed + swimSpeed, translateY + speed + swimSpeed)
  }

  def swim(translateX: Int, translateY: Int): Unit = {
    move(translateX, translateY)
  }
}

class Grandparent(name: String, age: Int, location: Location, val children: List[Parent])
  extends Person(name, age, location, 1)

class Parent(name: String, age: Int, location: Location, val children: List[Child], val grandParents: List[Grandparent])
  extends Person(name, age, location, 3) with Runner

class Child(name: String, age: Int, location: Location, val parents: List[Parent])
  extends Person(name, age, location, 5) with Runner with Swimmer



object Main {
  def main(args: Array[String]): Unit = {
    val grandparent = new Grandparent("조부모", 80, Location(0, 0), List())
    val parent = new Parent("부모", 50, Location(0, 0), List(), List(grandparent))
    val child = new Child("자식", 20, Location(0, 0), List(parent))

    System.out.println("")
    System.out.println("-----------------호적-----------------")
    val persons = List(grandparent, parent, child)
    persons.foreach { person =>
      System.out.println(s"${person.name} 나이: ${person.age}, 속도: ${person.speed}, 위치: ${person.location}")
    }
    System.out.println("--------------------------------------")
    System.out.println("")

    System.out.println("-----------------걷기-----------------")
    persons.foreach(_.move(1, 1))
    persons.foreach { person =>
      System.out.println(s"${person.name} 위치: ${person.location}")
    }
    System.out.println("--------------------------------------")
    System.out.println("")

    System.out.println("----------------뛰기-----------------")
    val runners = persons.collect { case runner: Runner => runner }
    runners.foreach(_.run(2, 2))
    runners.foreach { runner =>
      System.out.println(s"${runner.name} 위치: ${runner.location}")
    }
    System.out.println("--------------------------------------")
    System.out.println("")

    System.out.println("----------------수영---------------")
    val swimmers = persons.collect { case swimmer: Swimmer => swimmer }
    swimmers.foreach(_.swim(3, -1))
    swimmers.foreach { swimmer =>
      System.out.println(s"${swimmer.name} 위치: ${swimmer.location}")
    }
    System.out.println("--------------------------------------")

  }
}
