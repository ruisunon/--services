import React from "react";
import CustomFooter from "../components/shared/CustomFooter";
import Header from "../components/shared/Header";
import UserHero from "../components/users/UserHero";

const UsersPage = () => {
  return (
    <div>
      <Header />
      <UserHero />
      <CustomFooter />
    </div>
  );
};

export default UsersPage;
