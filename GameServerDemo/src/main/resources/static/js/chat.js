const { createApp, reactive, ref, onMounted, onBeforeUnmount } = Vue;

createApp({
	setup() {
		const messages = reactive([]);  // 聊天訊息陣列
		const newMessage = ref("");     // 新訊息輸入框
		let socket = null;
		let reconnectTimer = null;

		// 建立 WebSocket 連線
		const connectWebSocket = () => {
			socket = new WebSocket("ws://localhost:8080/game");

			socket.onopen = () => {
				console.log("WebSocket 連線成功");
				// 若之前有重連 timer，清掉
				if (reconnectTimer) {
					clearTimeout(reconnectTimer);
					reconnectTimer = null;
				}
			};

			socket.onmessage = (event) => {
				messages.push(event.data);
			};

			socket.onclose = () => {
				console.warn("WebSocket 斷線，2秒後嘗試重新連線...");
				// 避免多次 setTimeout 疊加
				if (!reconnectTimer) {
					reconnectTimer = setTimeout(connectWebSocket, 2000);
				}
			};

			socket.onerror = (err) => {
				console.error("WebSocket 錯誤", err);
				socket.close(); // 發生錯誤就斷線重連
			};
		};

		// 發送訊息
		const sendMessage = () => {
			const msg = newMessage.value.trim();
			if (msg === "") return;

			if (socket && socket.readyState === WebSocket.OPEN) {
				socket.send(msg);
				newMessage.value = "";
			} else {
				console.warn("WebSocket 尚未連線，無法發送訊息");
			}
		};

		// 取得歷史訊息 (可選，用 axios)
		const loadHistory = async () => {
			try {
				const res = await axios.get("/api/messages");
				res.data.forEach(msg => messages.push(msg));
			} catch (err) {
				console.error("載入歷史訊息失敗", err);
			}
		};

		// 當組件掛載時
		onMounted(() => {
			loadHistory();
			connectWebSocket();
		});

		// 當組件卸載時，關閉 WebSocket
		onBeforeUnmount(() => {
			if (socket) {
				socket.close();
			}
			if (reconnectTimer) {
				clearTimeout(reconnectTimer);
			}
		});

		return { messages, newMessage, sendMessage };
	}
}).mount("#app");
