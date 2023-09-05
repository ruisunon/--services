<template>
  <v-app id="app">
    <v-navigation-drawer
      v-if="isAuthenticated"
      permanent
      app
      class="light-blue lighten-5"
    >
      <h1 class="mt-5 text-h5 font-weight-bold">Concessionária</h1>
      <v-list nav flat class="mt-5">
        <v-list-item
          v-for="link in links"
          :key="link.title"
          link
          @click="$router.push(link.route)"
          class="text-h6"
        >
          <v-list-item-icon>
            <v-icon>{{ link.icon }}</v-icon>
          </v-list-item-icon>

          <v-list-item-content>
            <v-list-item-title>{{ link.title }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
      <v-btn @click="handleLogout" color="error">Sair</v-btn>
    </v-navigation-drawer>
    <v-main>
      <v-container fluid>
        <router-view />
      </v-container>
    </v-main>
    <v-footer app class="light-blue lighten-5 text-white py-3">
      <h4 class="mx-auto text-center">
        &copy; Concessionária 2022 - Todos Direitos Reservados
      </h4>
    </v-footer>
  </v-app>
</template>

<script>
import { mapActions, mapGetters } from 'vuex';

export default {
  data() {
    return {
      links: [
        { title: 'Home', icon: 'mdi-home', route: '/' },
        { title: 'Veículos', icon: 'mdi-car', route: '/vehicles' },
        { title: 'Vendas', icon: 'mdi-basket', route: '/sales' },
        { title: 'Vendedores', icon: 'mdi-account', route: '/sellers' }
      ]
    };
  },
  computed: { ...mapGetters(['isAuthenticated']) },
  methods: {
    ...mapActions(['Logout']),
    handleLogout() {
      this.Logout();
      this.$router.push('/login');
    }
  }
};
</script>

<style lang="scss">
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

nav {
  padding: 30px;

  a {
    font-weight: bold;
    color: #2c3e50;

    &.router-link-exact-active {
      color: #42b983;
    }
  }
}
</style>
