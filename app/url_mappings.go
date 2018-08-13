package app

import "github.com/bhaslop/bolaso/app/controllers"

func mapUrlsToControllers() {
	router.GET("/", controllers.GetHome)


	//login
	router.GET("/login", controllers.LoginHandler)
	router.GET("/callback", controllers.LoginCallbackHandler)
	router.POST("/callback", controllers.LoginCallbackHandler)
}
