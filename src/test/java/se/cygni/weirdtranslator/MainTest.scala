package se.cygni.weirdtranslator

import org.specs.Specification


class MainTest extends Specification {

  "extractUserDomain for input foo" should {
    val (user, domain) = Main.extractUserDomain("foo")
    "return user foo@gmail.com" in {
      user must_== ("foo@gmail.com")
    }
    "return domain gmail.com" in {
      domain must_== ("gmail.com")
    }
  }

  "extractUserDomain for input foo@bar" should {
    val (user, domain) = Main.extractUserDomain("foo@bar")
    "return user foo@bar" in {
      user must_== ("foo@bar")
    }
    "return domain bar" in {
      domain must_== ("bar")
    }
  }
}
