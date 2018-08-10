package app

import "github.com/bhaslop/bolaso/apptrollers"

func mapUrlsToControllers() {
	router.GET("/", controllers.GetHome)
}
