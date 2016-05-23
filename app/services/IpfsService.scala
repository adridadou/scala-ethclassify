package services

import javax.inject.Singleton

import org.ipfs.api.{IPFS, Multihash}
import org.ipfs.api.NamedStreamable.ByteArrayWrapper

/**
  * Created by davidroon on 18.03.16.
  * This code is released under Apache 2 license
  */
@Singleton
class IpfsService {
  val ipfs = new IPFS("/ip4/127.0.0.1/tcp/5001")

  def add(ipfsFile: ByteArrayWrapper):String = ipfs.add(ipfsFile).hash.toBase58

  def get(hash: String):Array[Byte] = ipfs.cat(Multihash.fromBase58(hash))
}
