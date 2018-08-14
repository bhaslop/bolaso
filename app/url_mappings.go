package app

import "github.com/bhaslop/bolaso/app/controllers"

func mapUrlsToControllers() {
	public := router.Group("/")
	private := router.Group("/")

	private.Use(controllers.IsAuthenticatedMiddleWare())

	public.GET("/", controllers.GetHome)
	private.GET("/user", controllers.UserHandler)


	//login
	public.GET("/login", controllers.LoginHandler)
	public.GET("/callback", controllers.LoginCallbackHandler)
	public.POST("/callback", controllers.LoginCallbackHandler)
	router.GET("/logout", controllers.LogoutHandler)
}
