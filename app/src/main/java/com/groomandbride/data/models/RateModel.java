package com.groomandbride.data.models;

public class RateModel {

    private boolean result;
    private String message;
    private DataBean data;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private int n;
        private int nModified;
        private OpTimeBean opTime;
        private String electionId;
        private int ok;
        private String operationTime;
        private $clusterTimeBean $clusterTime;

        public int getN() {
            return n;
        }

        public void setN(int n) {
            this.n = n;
        }

        public int getNModified() {
            return nModified;
        }

        public void setNModified(int nModified) {
            this.nModified = nModified;
        }

        public OpTimeBean getOpTime() {
            return opTime;
        }

        public void setOpTime(OpTimeBean opTime) {
            this.opTime = opTime;
        }

        public String getElectionId() {
            return electionId;
        }

        public void setElectionId(String electionId) {
            this.electionId = electionId;
        }

        public int getOk() {
            return ok;
        }

        public void setOk(int ok) {
            this.ok = ok;
        }

        public String getOperationTime() {
            return operationTime;
        }

        public void setOperationTime(String operationTime) {
            this.operationTime = operationTime;
        }

        public $clusterTimeBean get$clusterTime() {
            return $clusterTime;
        }

        public void set$clusterTime($clusterTimeBean $clusterTime) {
            this.$clusterTime = $clusterTime;
        }

        public static class OpTimeBean {
            /**
             * ts : 6744077546486235137
             * t : 9
             */

            private String ts;
            private int t;

            public String getTs() {
                return ts;
            }

            public void setTs(String ts) {
                this.ts = ts;
            }

            public int getT() {
                return t;
            }

            public void setT(int t) {
                this.t = t;
            }
        }

        public static class $clusterTimeBean {
            /**
             * clusterTime : 6744077546486235137
             * signature : {"hash":"Mw+sivM5l3r3JjS6jyOaNo+ggn8=","keyId":"6680380918038790146"}
             */

            private String clusterTime;
            private SignatureBean signature;

            public String getClusterTime() {
                return clusterTime;
            }

            public void setClusterTime(String clusterTime) {
                this.clusterTime = clusterTime;
            }

            public SignatureBean getSignature() {
                return signature;
            }

            public void setSignature(SignatureBean signature) {
                this.signature = signature;
            }

            public static class SignatureBean {
                /**
                 * hash : Mw+sivM5l3r3JjS6jyOaNo+ggn8=
                 * keyId : 6680380918038790146
                 */

                private String hash;
                private String keyId;

                public String getHash() {
                    return hash;
                }

                public void setHash(String hash) {
                    this.hash = hash;
                }

                public String getKeyId() {
                    return keyId;
                }

                public void setKeyId(String keyId) {
                    this.keyId = keyId;
                }
            }
        }
    }
}
