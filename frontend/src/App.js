import { useState } from "react";
import "./App.css";

function App() {
  const [glucose, setGlucose] = useState("");
  const [insulin, setInsulin] = useState(12);
  const [message, setMessage] = useState("");
  const [status, setStatus] = useState("NORMAL");
  const [showWarningModal, setShowWarningModal] = useState(false);

  const handleCalculate = async () => {
    if (!glucose) return;

    try {
      const response = await fetch("https://insulin-calculator-gu4j.onrender.com/api/calculate", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          glucose: Number(glucose),
          currentInsulin: insulin,
        }),
      });

      if (!response.ok) {
        throw new Error("서버 오류");
      }

      const data = await response.json();

      setInsulin(data.insulin);
      setStatus(data.status);
      setMessage(data.message);

      if (data.status === "WARNING"){
        setShowWarningModal(true);
      }

    } catch (error) {
      console.error(error);
      setMessage("서버와 통신 중 오류가 발생했습니다.");
    }
  };

  return (
      <div className="container">
        <header className="header">
          <div className="user">홍길동님</div>
          <div className="menu">☰</div>
        </header>

        <main>
          <p className="title">투여할 인슐린</p>

          <div className={`insulin ${status.toLowerCase()}`}>
            {insulin} <span>단위</span>
          </div>

          {message && <p className="message">{message}</p>}

          <p className="input-label">공복혈당을 입력하세요</p>

          <div className="input-box">
            <input
                type="number"
                value={glucose}
                onChange={(e) => setGlucose(e.target.value)}
                placeholder="숫자 입력"
            />
            <span>mg/dL</span>
          </div>

          <button
              className="calculate"
              onClick={handleCalculate}
              disabled={status === "WARNING" && showWarningModal}
          >
            계산하기
          </button>

          <p className="disclaimer">
            본 앱은 의료 판단을 대체하지 않습니다.
          </p>
        </main>

        {showWarningModal && (
            <div className="modal-overlay">
              <div className="modal">
                <h3>⚠️ 저혈당 경고</h3>
                <p>
                  현재 혈당 수치가 위험 수준입니다.<br />
                  즉시 담당 간호사 또는 의료진에게 연락하세요.
                </p>

                <button
                    className="modal-button"
                    onClick={() => setShowWarningModal(false)}
                >
                  확인
                </button>
              </div>
            </div>
        )}
      </div>



  );
}

export default App;