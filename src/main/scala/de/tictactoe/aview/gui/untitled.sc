import java.io.File

import javax.sound.sampled.AudioSystem

import scala.io.Source

val resourcesPath = getClass.getResource("/Music.wav")
println(resourcesPath.getPath)
