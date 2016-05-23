package services

import java.io.File

import java.security._
import java.security.spec.{PKCS8EncodedKeySpec, X509EncodedKeySpec}
import javax.crypto.spec.SecretKeySpec
import javax.crypto.{SecretKey, KeyGenerator, Cipher}
import javax.inject.Singleton

import com.google.common.io.Files

/**
  * Created by davidroon on 17.03.16.
  * This code is released under Apache 2 license
  */
@Singleton
class RSACryptoService {

  private val kpg = KeyPairGenerator.getInstance("RSA")
  kpg.initialize(2048)

  private def getCipher:Cipher = Cipher.getInstance("RSA")

  def encrypt(key: PublicKey, source:Array[Byte]) : Array[Byte] = {
    val cipher = getCipher
    cipher.init(Cipher.ENCRYPT_MODE, key)
    cipher.doFinal(source)
  }

  def decrypt(key:PrivateKey, source:Array[Byte]) : Array[Byte] = {
    val cipher = getCipher
    cipher.init(Cipher.DECRYPT_MODE, key)
    cipher.doFinal(source)
  }

  def generateKeypair() = kpg.genKeyPair()

  def serialize(key:PublicKey) : Array[Byte] = key.getEncoded

  def deserializePublicKey(data:Array[Byte]) :PublicKey = {
    val bobPubKeySpec = new X509EncodedKeySpec(data)
    val keyFactory = KeyFactory.getInstance("RSA")
    keyFactory.generatePublic(bobPubKeySpec)
  }

  def deserializePrivateKey(data:Array[Byte]) :PrivateKey = {
    val bobPrivKeySpec = new PKCS8EncodedKeySpec(data)
    val keyFactory = KeyFactory.getInstance("RSA")
    keyFactory.generatePrivate(bobPrivKeySpec)
  }
}

class AESCryptoService {
  private val kg = KeyGenerator.getInstance("AES")
  kg.init(new SecureRandom())

  private def getCipher = Cipher.getInstance("AES")

  def encrypt(key: SecretKey, source:Array[Byte]) : Array[Byte] = {
    val cipher = getCipher
    cipher.init(Cipher.ENCRYPT_MODE, key)
    cipher.doFinal(source)
  }

  def decrypt(key:SecretKey, file:File, target:File) : Unit = {
    val cipher = getCipher
    cipher.init(Cipher.DECRYPT_MODE, key)
    Files.write(cipher.doFinal(Files.toByteArray(file)), target)
  }

  def generateKey():SecretKey = kg.generateKey()

  def serialize(key:SecretKey):Array[Byte] = key.getEncoded

  def deserialize(data:Array[Byte]):SecretKey = new SecretKeySpec(data, 0, data.length, "AES")
}
