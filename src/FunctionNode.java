import java.util.List;

public class FunctionNode extends Node {
    FunctionNode(String _value){
        sValue = _value;

    }
    public String getValue(String[] obj, Toolkit tk, List<Node> roots, int numCalls, int maxCalls) {

            //{"L-CORE", "L-SURF", "L-O2", "L-BP", "SURF-STBL", "CORE-STBL", "BP-STBL", "COMFORT"}
        switch(sValue)
        {
            case "L-CORE":
                /*1. L-CORE (patient's internal temperature in C):
                high (> 37), mid (>= 36 and <= 37), low (< 36)*/
                switch (obj[0]) {
                    case "high":
                        return children.get(0).getValue(obj, tk, roots, numCalls, maxCalls);
                    case "mid":
                        return children.get(1).getValue(obj, tk, roots, numCalls, maxCalls);
                    case "low":
                        return children.get(2).getValue(obj, tk, roots, numCalls, maxCalls);
                    default:
                        Integer [] numberDecision = {0, 0, 0}; // I, S, A
                        for (int i = 0; i < 3; i++) {
                            String temp = children.get(i).getValue(obj, tk, roots, numCalls, maxCalls);
                            switch (temp) {
                                case "I" -> numberDecision[0] = numberDecision[0] + 1;
                                case "S" -> numberDecision[1] = numberDecision[1] + 1;
                                case "A" -> numberDecision[2] = numberDecision[2] + 1;
                            }

                        }
                        if (numberDecision[0] >= numberDecision[1]){
                            if (numberDecision[0] >= numberDecision[2]){
                                return "I";
                            }
                            else {
                                return "A";
                            }
                        }
                        else {
                            if (numberDecision[1] >= numberDecision[2]){
                                return "S";
                            }
                            else {
                                return "A";
                            }
                        }
                }
            /*2. L-SURF (patient's surface temperature in C):
            high (> 36.5), mid (>= 36.5 and <= 35), low (< 35)*/
            case "L-SURF":
                switch (obj[1]) {
                    case "high":
                        return children.get(0).getValue(obj, tk, roots, numCalls, maxCalls);
                    case "mid":
                        return children.get(1).getValue(obj, tk, roots, numCalls, maxCalls);
                    case "low":
                        return children.get(2).getValue(obj, tk, roots, numCalls, maxCalls);
                    default:
                        Integer [] numberDecision = {0, 0, 0}; // I, S, A
                        for (int i = 0; i < 3; i++) {
                            String temp = children.get(i).getValue(obj, tk, roots, numCalls, maxCalls);
                            switch (temp) {
                                case "I" -> numberDecision[0] = numberDecision[0] + 1;
                                case "S" -> numberDecision[1] = numberDecision[1] + 1;
                                case "A" -> numberDecision[2] = numberDecision[2] + 1;
                            }

                        }
                        if (numberDecision[0] >= numberDecision[1]){
                            if (numberDecision[0] >= numberDecision[2]){
                                return "I";
                            }
                            else {
                                return "A";
                            }
                        }
                        else {
                            if (numberDecision[1] >= numberDecision[2]){
                                return "S";
                            }
                            else {
                                return "A";
                            }
                        }
                }
            /*3. L-O2 (oxygen saturation in %):
            excellent (>= 98), good (>= 90 and < 98),
            fair (>= 80 and < 90), poor (< 80)*/
            case "L-O2":
                switch (obj[2]) {
                    case "excellent":
                        return children.get(0).getValue(obj, tk, roots, numCalls, maxCalls);
                    case "good":
                        return children.get(1).getValue(obj, tk, roots, numCalls, maxCalls);
                    case "fair":
                        return children.get(2).getValue(obj, tk, roots, numCalls, maxCalls);
                    case "poor":
                        return children.get(3).getValue(obj, tk, roots, numCalls, maxCalls);
                    default:
                        Integer [] numberDecision = {0, 0, 0}; // I, S, A
                        for (int i = 0; i < 4; i++) {
                            String temp = children.get(i).getValue(obj, tk, roots, numCalls, maxCalls);
                            switch (temp) {
                                case "I" -> numberDecision[0] = numberDecision[0] + 1;
                                case "S" -> numberDecision[1] = numberDecision[1] + 1;
                                case "A" -> numberDecision[2] = numberDecision[2] + 1;
                            }

                        }
                        if (numberDecision[0] >= numberDecision[1]){
                            if (numberDecision[0] >= numberDecision[2]){
                                return "I";
                            }
                            else {
                                return "A";
                            }
                        }
                        else {
                            if (numberDecision[1] >= numberDecision[2]){
                                return "S";
                            }
                            else {
                                return "A";
                            }
                        }
                }
            /*4. L-BP (last measurement of blood pressure):
            high (> 130/90), mid (<= 130/90 and >= 90/70), low (< 90/70)*/
            case "L-BP":
                switch (obj[3]) {
                    case "high":
                        return children.get(0).getValue(obj, tk, roots, numCalls, maxCalls);
                    case "mid":
                        return children.get(1).getValue(obj, tk, roots, numCalls, maxCalls);
                    case "low":
                        return children.get(2).getValue(obj, tk, roots, numCalls, maxCalls);
                    default:
                        Integer [] numberDecision = {0, 0, 0}; // I, S, A
                        for (int i = 0; i < 3; i++) {
                            String temp = children.get(i).getValue(obj, tk, roots, numCalls, maxCalls);
                            switch (temp) {
                                case "I" -> numberDecision[0] = numberDecision[0] + 1;
                                case "S" -> numberDecision[1] = numberDecision[1] + 1;
                                case "A" -> numberDecision[2] = numberDecision[2] + 1;
                            }

                        }
                        if (numberDecision[0] >= numberDecision[1]){
                            if (numberDecision[0] >= numberDecision[2]){
                                return "I";
                            }
                            else {
                                return "A";
                            }
                        }
                        else {
                            if (numberDecision[1] >= numberDecision[2]){
                                return "S";
                            }
                            else {
                                return "A";
                            }
                        }
                }
            /*5. SURF-STBL (stability of patient's surface temperature):
            stable, mod-stable, unstable*/
            case "SURF-STBL":
                switch (obj[4]) {
                    case "stable":
                        return children.get(0).getValue(obj, tk, roots, numCalls, maxCalls);
                    case "mod-stable":
                        return children.get(1).getValue(obj, tk, roots, numCalls, maxCalls);
                    case "unstable":
                        return children.get(2).getValue(obj, tk, roots, numCalls, maxCalls);
                    default:
                        Integer [] numberDecision = {0, 0, 0}; // I, S, A
                        for (int i = 0; i < 21; i++) {
                            String temp = children.get(i).getValue(obj, tk, roots, numCalls, maxCalls);
                            switch (temp) {
                                case "I" -> numberDecision[0] = numberDecision[0] + 1;
                                case "S" -> numberDecision[1] = numberDecision[1] + 1;
                                case "A" -> numberDecision[2] = numberDecision[2] + 1;
                            }

                        }
                        if (numberDecision[0] >= numberDecision[1]){
                            if (numberDecision[0] >= numberDecision[2]){
                                return "I";
                            }
                            else {
                                return "A";
                            }
                        }
                        else {
                            if (numberDecision[1] >= numberDecision[2]){
                                return "S";
                            }
                            else {
                                return "A";
                            }
                        }
                }
            /*6. CORE-STBL (stability of patient's core temperature)
            stable, mod-stable, unstable*/
            case "CORE-STBL":
                switch (obj[5]) {
                    case "stable":
                        return children.get(0).getValue(obj, tk, roots, numCalls, maxCalls);
                    case "mod-stable":
                        return children.get(1).getValue(obj, tk, roots, numCalls, maxCalls);
                    case "unstable":
                        return children.get(2).getValue(obj, tk, roots, numCalls, maxCalls);
                    default:
                        Integer [] numberDecision = {0, 0, 0}; // I, S, A
                        for (int i = 0; i < 3; i++) {
                            String temp = children.get(i).getValue(obj, tk, roots, numCalls, maxCalls);
                            switch (temp) {
                                case "I" -> numberDecision[0] = numberDecision[0] + 1;
                                case "S" -> numberDecision[1] = numberDecision[1] + 1;
                                case "A" -> numberDecision[2] = numberDecision[2] + 1;
                            }

                        }
                        if (numberDecision[0] >= numberDecision[1]){
                            if (numberDecision[0] >= numberDecision[2]){
                                return "I";
                            }
                            else {
                                return "A";
                            }
                        }
                        else {
                            if (numberDecision[1] >= numberDecision[2]){
                                return "S";
                            }
                            else {
                                return "A";
                            }
                        }
                }

            /*7. BP-STBL (stability of patient's blood pressure)
            stable, mod-stable, unstable*/
            case "BP-STBL":
                switch (obj[6]) {
                    case "stable":
                        return children.get(0).getValue(obj, tk, roots, numCalls, maxCalls);
                    case "mod-stable":
                        return children.get(1).getValue(obj, tk, roots, numCalls, maxCalls);
                    case "unstable":
                        return children.get(2).getValue(obj, tk, roots, numCalls, maxCalls);
                    default:
                        Integer [] numberDecision = {0, 0, 0}; // I, S, A
                        for (int i = 0; i < 3; i++) {
                            String temp = children.get(i).getValue(obj, tk, roots, numCalls, maxCalls);
                            switch (temp) {
                                case "I" -> numberDecision[0] = numberDecision[0] + 1;
                                case "S" -> numberDecision[1] = numberDecision[1] + 1;
                                case "A" -> numberDecision[2] = numberDecision[2] + 1;
                            }

                        }
                        if (numberDecision[0] >= numberDecision[1]){
                            if (numberDecision[0] >= numberDecision[2]){
                                return "I";
                            }
                            else {
                                return "A";
                            }
                        }
                        else {
                            if (numberDecision[1] >= numberDecision[2]){
                                return "S";
                            }
                            else {
                                return "A";
                            }
                        }
                }

            /*8. COMFORT (patient's perceived comfort at discharge, measured as
            an integer between 0 and 20)*/
            case "COMFORT":
                switch (obj[7]) {
                    case "00":
                        return children.get(0).getValue(obj, tk, roots, numCalls, maxCalls);
                    case "01":
                        return children.get(1).getValue(obj, tk, roots, numCalls, maxCalls);
                    case "02":
                        return children.get(2).getValue(obj, tk, roots, numCalls, maxCalls);
                    case "03":
                        return children.get(3).getValue(obj, tk, roots, numCalls, maxCalls);
                    case "04":
                        return children.get(4).getValue(obj, tk, roots, numCalls, maxCalls);
                    case "05":
                        return children.get(5).getValue(obj, tk, roots, numCalls, maxCalls);
                    case "06":
                        return children.get(6).getValue(obj, tk, roots, numCalls, maxCalls);
                    case "07":
                        return children.get(7).getValue(obj, tk, roots, numCalls, maxCalls);
                    case "08":
                        return children.get(8).getValue(obj, tk, roots, numCalls, maxCalls);
                    case "09":
                        return children.get(9).getValue(obj, tk, roots, numCalls, maxCalls);
                    case "10":
                        return children.get(10).getValue(obj, tk, roots, numCalls, maxCalls);
                    case "11":
                        return children.get(11).getValue(obj, tk, roots, numCalls, maxCalls);
                    case "12":
                        return children.get(12).getValue(obj, tk, roots, numCalls, maxCalls);
                    case "13":
                        return children.get(13).getValue(obj, tk, roots, numCalls, maxCalls);
                    case "14":
                        return children.get(14).getValue(obj, tk, roots, numCalls, maxCalls);
                    case "15":
                        return children.get(15).getValue(obj, tk, roots, numCalls, maxCalls);
                    case "16":
                        return children.get(16).getValue(obj, tk, roots, numCalls, maxCalls);
                    case "17":
                        return children.get(17).getValue(obj, tk, roots, numCalls, maxCalls);
                    case "18":
                        return children.get(18).getValue(obj, tk, roots, numCalls, maxCalls);
                    case "19":
                        return children.get(19).getValue(obj, tk, roots, numCalls, maxCalls);
                    case "20":
                        return children.get(20).getValue(obj, tk, roots, numCalls, maxCalls);
                    default:
                        Integer [] numberDecision = {0, 0, 0, 0}; // I, S, A, F
                        for (int i = 0; i < 21; i++) {
                            String temp = children.get(i).getValue(obj, tk, roots, numCalls, maxCalls);
                            switch (temp) {
                                case "I" -> numberDecision[0] = numberDecision[0] + 1;
                                case "S" -> numberDecision[1] = numberDecision[1] + 1;
                                case "A" -> numberDecision[2] = numberDecision[2] + 1;
                                case "F" -> numberDecision[3] = numberDecision[3] + 1;

                            }

                            //ADDED for speedup of code
                            if (numberDecision[3] > 11){
                                return "F";
                            }
                            if (numberDecision[2] > 11){
                                return "A";
                            }
                            if (numberDecision[1] > 11){
                                return "S";
                            }
                            if (numberDecision[0] > 11){
                                return "I";
                            }
                            if (numCalls>=1){ //FORCE THIS TO QUIT. TAKES TOO LONG IN LOOP
                                return "F";
                            }

                        }

                        if (numberDecision[0] >= numberDecision[1]){
                            if (numberDecision[0] >= numberDecision[2]){
                                return "I";
                            }
                            else {
                                return "A";
                            }
                        }
                        else {
                            if (numberDecision[1] >= numberDecision[2]){
                                return "S";
                            }
                            else {
                                return "A";
                            }
                        }
                }

        }
        return "";
    }
}
