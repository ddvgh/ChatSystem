const { createApp, reactive} = Vue;

createApp({
	setup() {
		const loginData = reactive({
			password: "",
			email: "",
		});

		function login() {
			axios.post("/api/login", { password: loginData.password, email: loginData.email },
				{
					headers: { "Content-Type": "application/json" },
				},
			).then((res) => {
				console.log("登入成功", res.data);
				alert("登入成功")
				window.location.href = "chat.html"
			}).catch((err) => {
				console.error("錯誤", err);
				alert("登入失敗")
			});

		}
		return {login, loginData}
	},
}).mount("#app");
