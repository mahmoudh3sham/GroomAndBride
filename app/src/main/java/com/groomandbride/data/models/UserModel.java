package com.groomandbride.data.models;

import java.util.List;

import androidx.annotation.Nullable;

public class UserModel {

    @Nullable
    private String message;
    @Nullable
    private boolean result;
    @Nullable
    private UserBean user;

    @Nullable
    public String getMessage() {
        return message;
    }

    public void setMessage(@Nullable String message) {
        this.message = message;
    }
    @Nullable
    public boolean isResult() {
        return result;
    }

    public void setResult(@Nullable boolean result) {
        this.result = result;
    }
    @Nullable
    public UserBean getUser() {
        return user;
    }

    public void setUser(@Nullable UserBean user) {
        this.user = user;
    }

    public static class UserBean {

        private String _id;
        private String userName;
        private String userEmail;
        private String userPassword;
        private boolean isAdmin;
        private UserRoleBean userRole;
        private String date;
        private int __v;
        private String token;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserEmail() {
            return userEmail;
        }

        public void setUserEmail(String userEmail) {
            this.userEmail = userEmail;
        }

        public String getUserPassword() {
            return userPassword;
        }

        public void setUserPassword(String userPassword) {
            this.userPassword = userPassword;
        }

        public boolean isIsAdmin() {
            return isAdmin;
        }

        public void setIsAdmin(boolean isAdmin) {
            this.isAdmin = isAdmin;
        }

        public UserRoleBean getUserRole() {
            return userRole;
        }

        public void setUserRole(UserRoleBean userRole) {
            this.userRole = userRole;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int get__v() {
            return __v;
        }

        public void set__v(int __v) {
            this.__v = __v;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public static class UserRoleBean {

            private String _id;
            private String role;
            private int __v;
            private List<ActionsBean> actions;
            private List<?> sideNavActions;

            public String get_id() {
                return _id;
            }

            public void set_id(String _id) {
                this._id = _id;
            }

            public String getRole() {
                return role;
            }

            public void setRole(String role) {
                this.role = role;
            }

            public int get__v() {
                return __v;
            }

            public void set__v(int __v) {
                this.__v = __v;
            }

            public List<ActionsBean> getActions() {
                return actions;
            }

            public void setActions(List<ActionsBean> actions) {
                this.actions = actions;
            }

            public List<?> getSideNavActions() {
                return sideNavActions;
            }

            public void setSideNavActions(List<?> sideNavActions) {
                this.sideNavActions = sideNavActions;
            }

            public static class ActionsBean {

                private String _id;
                private List<String> Halls;
                private List<String> Users;
                private List<?> Admin;
                private List<?> Other;

                public String get_id() {
                    return _id;
                }

                public void set_id(String _id) {
                    this._id = _id;
                }

                public List<String> getHalls() {
                    return Halls;
                }

                public void setHalls(List<String> Halls) {
                    this.Halls = Halls;
                }

                public List<String> getUsers() {
                    return Users;
                }

                public void setUsers(List<String> Users) {
                    this.Users = Users;
                }

                public List<?> getAdmin() {
                    return Admin;
                }

                public void setAdmin(List<?> Admin) {
                    this.Admin = Admin;
                }

                public List<?> getOther() {
                    return Other;
                }

                public void setOther(List<?> Other) {
                    this.Other = Other;
                }
            }
        }
    }
}
