package com.Samsara.Capstone.Project.Model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PredictionInput {
        private String area;
        private String bedroom;
        private String bathroom;
        private String stories;
        private String parking;
        private String mainroad;
        private String guestroom;
        private String basement;
        private String hotwaterheating;
        private String prefarea;
        private String airconditioning;
        private String furnishingstatus;

        public PredictionInput() {
        }
}
