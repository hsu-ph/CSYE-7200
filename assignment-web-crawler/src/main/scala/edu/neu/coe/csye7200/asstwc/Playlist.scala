import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import io.circe._
import io.circe.parser._
import io.circe.generic.auto._
import requests._

case class Playlist(id: String, name: String, followers: Int)

case class Artist(id: String, name: String, followers: Int)

case class Track(name: String, duration_ms: Long, artists: List[Playlist])

object SpotifyPlaylistAnalyzer {
  val playlistId = "5Rrf7mqN8uus2AaQQQNdc1"
  val playlistUrl = s"https://api.spotify.com/v1/playlists/$playlistId"
  val artistId = "0TnOYISbd1XYRBk9myaseg"
  val artistUrl = s"https://api.spotify.com/v1/artists/0TnOYISbd1XYRBk9myaseg"

  def getPlaylist(): Future[List[Track]] = {
    val response = requests.get(
      playlistUrl,
      headers = Map("Authorization" -> "BQByO1OzZD8dD97Zd3yDNEHIh9pxGLPG-fMCKV55zGaThypqPC83FKGjq2AhzhAm4_KkvOyYDIYkASmo15mAAxlObp7MKo0aw6XIflvWu9Hr8z8M7dc")
    )
    val tracksJson = parse(response.text)

    tracksJson match {
      case Left(parseFailure) =>
        println(s"Failed to parse JSON: $parseFailure")
        Future.successful(List.empty)
      case Right(json) =>
        val topTracks = json.hcursor.downField("tracks").downField("items").as[List[Json]].getOrElse(List.empty).map { trackJson =>
          val name = trackJson.hcursor.downField("track").downField("name").as[String].getOrElse("")
          val duration = trackJson.hcursor.downField("track").downField("duration_ms").as[Long].getOrElse(0L)
          val artists = trackJson.hcursor.downField("track").downField("artists").as[List[Json]].getOrElse(List.empty).map { artistJson =>
            val artistName = artistJson.hcursor.downField("name").as[String].getOrElse("")
            val artistId = artistJson.hcursor.downField("id").as[String].getOrElse("")
            Playlist(artistName, artistId, 0)
          }
          Track(name, duration, artists)
        }
        topTracks.foreach { track =>
          println(s"Track: ${track.name}, Duration: ${track.duration_ms}, Artists: ${track.artists.map(_.name).mkString(", ")}")
        }
        Future.successful(topTracks)
    }
  }

  def getArtist(artistId: String): Future[Artist] = {
    val artistUrl = s"https://api.spotify.com/v1/artists/0TnOYISbd1XYRBk9myaseg"
    val accessToken = "BQByO1OzZD8dD97Zd3yDNEHIh9pxGLPG-fMCKV55zGaThypqPC83FKGjq2AhzhAm4_KkvOyYDIYkASmo15mAAxlObp7MKo0aw6XIflvWu9Hr8z8M7dc"
    val response = requests.get(
      artistUrl,
      headers = Map("Authorization" -> s"Bearer $accessToken")
    )
    response.statusCode match {
      case 200 =>
        val artistJson = parse(response.text).getOrElse(Json.Null)
        val artistName = artistJson.hcursor.downField("name").as[String].getOrElse("")
        val followers = artistJson.hcursor.downField("followers").downField("total").as[Int].getOrElse(0)
        Future.successful(Artist(artistId, artistName, followers))
      case _ =>
        println(s"Failed to fetch artist details for ID $artistId")
        Future.successful(Artist("", "Unknown Artist", 0)) // Return a default Artist object
    }
  }


  def main(args: Array[String]): Unit = {
    val playlistFuture = getPlaylist()
    playlistFuture.flatMap { tracks =>
      if (tracks.isEmpty) {
        println("No tracks found in the playlist")
        Future.successful(())
      } else {
        println("Part 1:")
        tracks.take(10).zipWithIndex.foreach { case (track, index) =>
          println(s"Songname${index + 1} , ${track.name} , ${track.duration_ms}")
        }

        val artistFutures = tracks.flatMap(_.artists).distinctBy(_.id).take(10).map { artist =>
          getArtist(artist.id).map { detailedArtist =>
            (artist, detailedArtist)
          }
        }

        Future.sequence(artistFutures).map { detailedArtists =>
          val sortedArtists = detailedArtists.sortBy(_._2.followers).reverse
          println("\nPart 2:")
          sortedArtists.foreach { case (artist, detailedArtist) =>
            println(s"${artist.name}: ${detailedArtist.followers}")
          }
        }
      }
    }.onComplete {
      case scala.util.Success(_) => println("Success")
      case scala.util.Failure(exception) => println(s"Error: ${exception.getMessage}")
    }
  }
}
