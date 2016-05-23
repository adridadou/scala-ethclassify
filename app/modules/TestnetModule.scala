package modules

import com.google.inject.AbstractModule
import org.adridadou.ethereum.EthereumFacade
import org.adridadou.ethereum.provider.TestnetEthereumFacadeProvider
/**
  * Created by davidroon on 28.04.16.
  * This code is released under Apache 2 license
  */
class TestnetModule extends AbstractModule{
  override def configure(): Unit = {
    val provider = new TestnetEthereumFacadeProvider()
    val key = provider.getKey("cow", "")
    bind(classOf[EthereumFacade]).toInstance(provider.create(key))
  }
}
