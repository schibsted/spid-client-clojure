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

Finally, call the API with:

```clj
(GET client "/users")
```

Note: The `:redirect-uri` really makes no sense in this context, but
is required by the API. It only comes into play when logging in as a user.
