# spid-sdk-clojure

A Clojure wrapper for the SPID Java SDK.

## Install

First get sdk-java installed in your .m2:

```sh
git clone https://github.com/schibsted/sdk-java.git
cd sdk-java
mvn install
```

Then install spid-sdk-clojure:

```sh
cd spid-sdk-clojure
lein install
```

Finally use it in your project.clj:

```clj
[spid-sdk-clojure "0.2.1"]
```

## Usage

### Server to server communication

Create a server client with:

```clj
(def client (create-server-client client-id secret))
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

### Calling API on behalf of users

Create a user client with:

```clj
(def client (create-user-client code client-id secret))
```

This is the `code` you get after a user has logged in on SPiD.

Otherwise it works just like the server-to-server API.
