package services

import java.io.File
import javax.crypto.SecretKey
import javax.inject.{Inject, Singleton}

import org.mapdb.{DBMaker, HTreeMap}
import play.api.i18n.MessagesApi

/**
  * Created by davidroon on 18.03.16.
  * This code is released under Apache 2 license
  */
@Singleton
class DocumentKeyService @Inject() (val aes: AESCryptoService){

  private val db = DBMaker.fileDB(new File("key.db")).make()
  private val fileKeys = db.hashMap("fileKeys").asInstanceOf[HTreeMap[String, Array[Byte]]]

  def save(hash: String, key: SecretKey) = {
    fileKeys.put(hash,aes.serialize(key))
    db.commit()
  }

  def get(hash:String) :Option[SecretKey] = Option(fileKeys.get(hash)).map(aes.deserialize)

}
