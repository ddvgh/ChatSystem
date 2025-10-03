const { createApp, ref, onMounted, onBeforeUnmount } = Vue;

createApp({
	setup() {
		const messages = ref([]);   // 聊天訊息陣列
		const newMessage = ref(""); // 新訊息輸入框
		let socket = null;
		let reconnectTimer = null;

		// 建立 WebSocket 連線
		const connectWebSocket = () => {
			socket = new WebSocket("ws://localhost:8080/game");

			socket.onopen = () => {
				console.log("WebSocket 連線成功");
				if (reconnectTimer) {
					clearTimeout(reconnectTimer);
					reconnectTimer = null;
				}
			};

			socket.onmessage = (event) => {
				try {
					const msg = JSON.parse(event.data);
					messages.value.push(msg);
				} catch {
					messages.value.push(event.data);
				}
			};

			socket.onclose = () => {
				console.warn("WebSocket 斷線，2秒後嘗試重新連線...");
				if (!reconnectTimer) {
					reconnectTimer = setTimeout(connectWebSocket, 2000);
				}
			};

			socket.onerror = (err) => {
				console.error("WebSocket 錯誤", err);
				socket.close();
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

		// 取得歷史訊息
		const loadHistory = async () => {
			try {
				const roomId = 1; // 假設要撈 1 號房間
				const res = await axios.get(`/api/messages/${roomId}`);
				res.data.forEach(msg => messages.value.push(msg.senderName + ": " + msg.content));
			} catch (err) {
				console.error("載入歷史訊息失敗", err);
			}
		};

		onMounted(() => {
			loadHistory();
			connectWebSocket();
		});

		onBeforeUnmount(() => {
			if (socket) socket.close();
			if (reconnectTimer) clearTimeout(reconnectTimer);
		});

		return { messages, newMessage, sendMessage };
	}
}).mount("#app");
