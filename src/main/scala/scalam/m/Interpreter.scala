package scalam.m

import scalax.file.Path
import scala.sys.process._
import scala.io._
import java.io._
import scala.concurrent._

class Interpreter(command: String, pwd: Path) {

  private val inputStream = new SyncVar[OutputStream];

  val process = Process(command, pwd.fileOption, "" -> "").run(
    new ProcessIO(
      stdin => inputStream.put(stdin),
      stdout => Source.fromInputStream(stdout).getLines.foreach(println),
      stderr => Source.fromInputStream(stderr).getLines.foreach(println)));

  def write(s: String): Unit = synchronized {
    inputStream.get.write((s + "\n").getBytes)
    inputStream.get.flush()
  }

  def close(): Unit = {
    inputStream.get.close
  }
}