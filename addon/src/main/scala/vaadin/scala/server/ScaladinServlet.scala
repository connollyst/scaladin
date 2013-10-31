package vaadin.scala.server

import com.vaadin.server.{ VaadinServlet, SessionInitListener, SessionInitEvent }
import javax.servlet.ServletConfig
import vaadin.scala.mixins.ScaladinServletServiceMixin
import vaadin.scala.internal.{ WrapperUtil, ScaladinUIProvider }
import vaadin.scala.ScaladinServletService

class ScaladinServlet extends VaadinServlet {

  override def init(servletConfig: ServletConfig) {
    super.init(servletConfig)
    registerUIProvider()
  }

  private def registerUIProvider() {
    service.sessionInitListeners += {
      _.session.p.addUIProvider(new ScaladinUIProvider) // FIXME .p.
    }
  }

  override def createServletService(c: com.vaadin.server.DeploymentConfiguration) =
    new ScaladinServletService(new com.vaadin.server.VaadinServletService(this, c) with ScaladinServletServiceMixin).init().p

  def service: ScaladinServletService = WrapperUtil.wrapperFor(getService).get
}