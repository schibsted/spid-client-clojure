# sdk-clojure

A Clojure wrapper for the SPID Java SDK.

## Usage

First get sdk-java installed in your .m2:

```sh
git clone https://github.com/schibsted/sdk-java.git
cd sdk-java
mvn install
```

Then start the repl with `lein repl`.

Create a client with:

```clj
(def client (create-client client-id secret))
```

You can also pass in an option map. These are the defaults:

```clj
{:spp-base-url "https://stage.payment.schibsted.no"
 :redirect-uri "http://localhost:8080"}
```

In order for the redirect URL to make any sense (this is only used when logging
in with OAuth), you must run the API explorer from the java-sdk:

```sh
cd sdk-java/example
mvn jetty:run
```

This is entirely optional.

Finally, call the API with:

```clj
(GET client "/users")
```
