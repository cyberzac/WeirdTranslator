package se.cygni.weirdtranslator

import org.specs.Specification


class MainSpec extends Specification {

  "extractUserAndDomain for input foo" should {
    val (user, domain) = Main.extractUserAndDomain("foo")
    "return user foo@gmail.com" in {
      user must_== ("foo@gmail.com")
    }
    "return domain gmail.com" in {
      domain must_== ("gmail.com")
    }
  }

  "extractUserAndDomain for input foo@bar" should {
    val (user, domain) = Main.extractUserAndDomain("foo@bar")
    "return user foo@bar" in {
      user must_== ("foo@bar")
    }
    "return domain bar" in {
      domain must_== ("bar")
    }
  }

}
