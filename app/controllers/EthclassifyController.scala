package controllers

import java.io.File
import javax.inject.Inject

import com.google.common.io.Files
import org.ipfs.api.NamedStreamable
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.{Action, Controller}
import services.{DocumentKeyService, IpfsService, AESCryptoService, RSACryptoService}

import scala.concurrent.ExecutionContext

/**
  * Created by davidroon on 17.03.16.
  * This code is released under Apache 2 license
  */
class EthclassifyController @Inject() (val messagesApi: MessagesApi, val rsa:RSACryptoService, val aes: AESCryptoService, val ipfs:IpfsService, val documentKeyService:DocumentKeyService)
                                      (implicit ec: ExecutionContext) extends Controller with I18nSupport{

  def upload = Action(parse.temporaryFile) { request =>
    val tmpFile = File.createTempFile("crypto","tmp")
    request.body.moveTo(tmpFile)

    val key = aes.generateKey()
    val result = aes.encrypt(key,Files.toByteArray(tmpFile))
    val ipfsFile = new NamedStreamable.ByteArrayWrapper("ipfs.file", result)
    val hash = ipfs.add(ipfsFile)

    documentKeyService.save(hash, key)

    Ok(hash)
  }

  def decrypt(hash:String, key:String) = Action { request =>
    documentKeyService.get(hash)
    val result = ipfs.get(hash)

    Ok("")
  }
}
