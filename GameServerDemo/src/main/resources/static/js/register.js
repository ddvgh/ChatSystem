const { createApp, reactive} = Vue;

createApp({
	setup() {
		const registerData = reactive({
			username: "",
			password: "",
			email: "",
		});

		function register() {
			axios.post("/api/register", { username: registerData.username, password: registerData.password, email: registerData.email },
				{
					headers: { "Content-Type": "application/json" },
				},
			).then((res) => {
				console.log("註冊成功", res.data);
				alert(res.data.user + "" + "註冊成功")
				window.location.href = "login.html"
			}).catch((err) => {
				console.error("錯誤", err);
				alert("註冊失敗" + "，" + err.response.data.message)
			});

		}
		return {register, registerData}
	},
}).mount("#app");
