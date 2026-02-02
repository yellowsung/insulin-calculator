package com.insulin.backend.service;

import org.springframework.stereotype.Service;

@Service
public class InsulinService {

    // 기준값 (지금은 하드코딩, 나중에 설정/DB로 이동)
    private static final int TARGET_MIN = 100;
    private static final int TARGET_MAX = 140;
    private static final int CRITICAL_POINT = 70;

    /**
     * 공복 혈당을 기준으로 인슐린 단위를 계산한다.
     *
     * @param glucose        오늘 공복 혈당
     * @param currentInsulin 현재 인슐린 단위
     * @return 계산 결과
     */
    public Result calculate(int glucose, int currentInsulin) {

        // 1️⃣ 저혈당 위험
        if (glucose <= CRITICAL_POINT) {
            return new Result(
                    currentInsulin,
                    Status.WARNING,
                    "저혈당 위험입니다. 인슐린 투여를 중단하세요."
            );
        }

        // 2️⃣ 목표 혈당 범위
        if (glucose >= TARGET_MIN && glucose <= TARGET_MAX) {
            return new Result(
                    currentInsulin,
                    Status.NORMAL,
                    "목표 혈당 범위 내입니다."
            );
        }

        // 3️⃣ 혈당이 높은 경우 → +2
        if (glucose > TARGET_MAX) {
            return new Result(
                    currentInsulin + 2,
                    Status.INCREASE,
                    "혈당이 높아 2단위 증가했습니다."
            );
        }

        // 4️⃣ 혈당이 낮은 경우 → -2
        return new Result(
                Math.max(currentInsulin - 2, 0),
                Status.DECREASE,
                "혈당이 낮아 2단위 감소했습니다."
        );
    }

    // 상태 값 (프론트/컨트롤러와 공유 가능)
    public enum Status {
        NORMAL,
        INCREASE,
        DECREASE,
        WARNING
    }

    // Service 결과 객체 (record 대신 일반 class)
    public static class Result {

        private final int insulin;
        private final Status status;
        private final String message;

        public Result(int insulin, Status status, String message) {
            this.insulin = insulin;
            this.status = status;
            this.message = message;
        }

        public int getInsulin() {
            return insulin;
        }

        public Status getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }
    }
}