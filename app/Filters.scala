/**
  * Cubefriendly
  * Created by davidroon on 17.03.16.
  * This code is released under Apache 2 license
  */
import javax.inject.Inject

import play.api.http.HttpFilters
import play.filters.cors.CORSFilter

class Filters @Inject() (corsFilter: CORSFilter) extends HttpFilters {
  def filters = Seq(corsFilter)
}
