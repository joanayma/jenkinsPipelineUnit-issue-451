import foo.MyClass

def call() {
  MyClass myObject
  pipeline {
    stage("test") {
      script {
        myObject = new MyClass(env) //
      }
    }
    stage("stage2") {
      script {
        myObject.run(this, "command")
      }
    }
  }
}
