package controllers

import (
	"github.com/gin-gonic/gin"
	"golang.org/x/oauth2"
	"os"
	"context"
	"encoding/json"
	"net/http"
	"crypto/rand"
	"encoding/base64"
	"github.com/gin-contrib/sessions"
	"net/url"
	"fmt"
	"log"
	"github.com/bhaslop/bolaso/app/service"
)

var (
	authDomain = os.Getenv("AUTH0_DOMAIN")
	authConfig = &oauth2.Config{
		ClientID:     os.Getenv("AUTH0_CLIENT_ID"),
		ClientSecret: os.Getenv("AUTH0_CLIENT_SECRET"),
		RedirectURL:  os.Getenv("AUTH0_CALLBACK_URL"),
		Scopes:       []string{"openid", "profile"},
		Endpoint: oauth2.Endpoint{
			AuthURL:  "https://" + authDomain + "/authorize",
			TokenURL: "https://" + authDomain + "/oauth/token",
		},
	}
)

func LoginCallbackHandler(c *gin.Context) {
	var stateSession string

	state := c.Query("state")
	session := sessions.Default(c)

	stateSession = session.Get("state").(string)

	fmt.Println("sesseion: " + stateSession)
	fmt.Println("param: " + state)

	if state != stateSession {
		panic("Invalid state parameter")
	}

	code := c.Query("code")

	token, err := authConfig.Exchange(context.TODO(), code)

	fmt.Println(token)

	if err != nil {
		c.Error(err)
		return
	}

	client := authConfig.Client(context.TODO(), token)

	resp, err := client.Get("https://" + authDomain + "/userinfo")

	fmt.Println("Getting userinfo...")

	if err != nil {
		fmt.Println("Error con userinfo get")
		log.Panic(err)
		c.Error(err)
		return
	}

	defer resp.Body.Close()

	var profile map[string]interface{}

	if err = json.NewDecoder(resp.Body).Decode(&profile); err != nil {
		fmt.Println("Error parsing body!")
		c.Error(err)
		return
	}

	session.Set("id_token", token.Extra("id_token"))
	session.Set("access_token", token.AccessToken)
	session.Set("profile", profile)

	err = session.Save()

	if err != nil {
		fmt.Println("Error saving session")
		fmt.Println(err)
		c.Error(err)
		return
	}

	fmt.Println("Redirecting...")

	c.Redirect(http.StatusSeeOther, "/user")
}

func LoginHandler(c *gin.Context) {
	aud := os.Getenv("AUTH0_AUDIENCE")
	session := sessions.Default(c)

	if aud == "" {
		aud = "https://" + authDomain + "/userinfo"
	}

	b := make([]byte, 32)

	rand.Read(b)

	state := base64.StdEncoding.EncodeToString(b)

	session.Set("state", state)

	err := session.Save()

	if err != nil {
		c.Error(err)
		return
	}

	audience := oauth2.SetAuthURLParam("audience", aud)
	url := authConfig.AuthCodeURL(state, audience)

	c.Redirect(http.StatusTemporaryRedirect, url)
}

func LogoutHandler(c *gin.Context) {
	var Url *url.URL

	Url, err := url.Parse("https://" + authDomain)

	if err != nil {
		panic("boom")
	}

	Url.Path += "/v2/logout"

	parameters := url.Values{}

	parameters.Add("returnTo", os.Getenv("AUTH0_CALLBACK_URL"))
	parameters.Add("client_id", os.Getenv("AUTH0_CLIENT_ID"))

	Url.RawQuery = parameters.Encode()

	c.Redirect(http.StatusTemporaryRedirect, Url.String())
}

func IsAuthenticatedMiddleWare() gin.HandlerFunc {
	return func(c *gin.Context) {
		if IsAuthenticated(c) {
			c.Next()
		} else {
			c.Redirect(http.StatusTemporaryRedirect, "/login")
		}
	}
}

func IsAuthenticated(c *gin.Context) bool {
	return service.GetUserFromSession(c) != nil
}
