# spid-sdk-clojure

A Clojure wrapper for the SPID Java SDK.

## Install

Add `[spid-sdk-clojure "0.5.4"]` to `:dependencies` in your `project.clj`.

## Usage

Start by creating a client:

```clj
(def client (create-client client-id secret))
```

You can also pass in an option map. These are the defaults:

```clj
{:spp-base-url "https://stage.payment.schibsted.no"
 :redirect-uri "http://localhost:8080"}
```

Note: The `:redirect-uri` really makes no sense in this context, but
is required by the API. It only comes into play when logging in as a user.

### Server to server communication

Using the client, create a server token:

```clj
(def token (create-server-token client))
```

Then call the API with:

```clj
(GET client token "/users")
```

It can also take an optional parameters map.

### Calling API on behalf of users

Create a user token with:

```clj
(def token (create-user-token client code))
```

This is the `code` you get after a user has logged in on SPiD.

Otherwise it works just like the server-to-server API.

```clj
(GET client token "/me")
```
