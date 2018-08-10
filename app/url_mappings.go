package app

import "github.com/bhaslop/bolaso/app/controllers"

func mapUrlsToControllers() {
	router.GET("/", controllers.GetHome)
}
