# MovieQuester

A teaching project showing how to consume REST web services in Android with
**Retrofit**. It is deliberately small and readable: the point is the wiring,
not the app.

If you have forked this to learn from, start with `api/` and `model/` — that is
where the interesting part lives.

## What it demonstrates

- **A reusable Retrofit client** (`api/ApiClient.java`) — one configured
  `Retrofit` instance with a Gson converter, rather than rebuilding it per call.
- **The API surface as an interface** (`api/ApiInterface.java`) — endpoints
  declared with annotations, so the HTTP details stay in one place and the rest
  of the app just calls methods.
- **Typed response models** (`model/`) — `MoviesResponse`, `MovieData`, `Genre`,
  `ProductionCompany` and friends. JSON is parsed into real types at the
  boundary instead of being passed around as maps.
- **Binding results to a list** (`adapter/MovieClassAdapter.java`) — a
  `RecyclerView` adapter rendering the parsed response.
- **A second endpoint shape** (`model/polylines/`) — Google Directions route and
  polyline models, to show the same client handling a differently shaped API.

## Running it

1. Clone, open in Android Studio, let Gradle sync.
2. Add your own API key in `util/AppConstants.java`.
3. Run on a device or emulator.

The movie endpoints expect a TMDB API key. Get a free one at
[themoviedb.org](https://www.themoviedb.org/settings/api).

## A note on the age of this code

This was written as a Java teaching example and is kept as-is because people
have forked it. In new Android work I would reach for Kotlin, coroutines and a
repository layer rather than calling Retrofit from the activity — see
[RizTech Academy](https://riztechacademy.com) for current material.

## Licence

Free to use for learning. Attribution welcome, not required.
