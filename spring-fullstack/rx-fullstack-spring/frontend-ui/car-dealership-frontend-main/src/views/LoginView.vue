<template>
  <main>
    <h1>Concessionária</h1>
    <v-form @submit="handleSubmit" class="mt-10">
      <v-container>
        <v-row justify="center">
          <v-col sm="12" md="6" lg="4">
            <v-text-field v-model="email" type="email" label="Email">
            </v-text-field>
            <v-text-field v-model="password" type="password" label="Senha">
            </v-text-field>
            <v-btn type="submit" color="primary">Login</v-btn>
          </v-col>
        </v-row>
      </v-container>
    </v-form>
  </main>
</template>

<script>
import { mapActions } from 'vuex';

export default {
  name: 'LoginView',
  data() {
    return {
      email: '',
      password: ''
    };
  },
  methods: {
    ...mapActions(['Login']),
    async handleSubmit(e) {
      e.preventDefault();
      try {
        await this.$store.dispatch('Login', {
          email: this.email,
          password: this.password
        });
        this.$router.push('/');
      } catch (err) {
        alert(err);
      }
    }
  }
};
</script>
