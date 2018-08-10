package app

import "github.com/bhaslop/bolaso/src/app/controllers"

func mapUrlsToControllers() {
	router.GET("/", controllers.GetHome)
}
