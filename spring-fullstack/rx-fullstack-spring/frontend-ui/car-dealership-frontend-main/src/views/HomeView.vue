<template>
  <main>
    <h2>Home</h2>
    <v-container>
      <v-row>
        <v-col>
          <v-card elevation="2" class="">
            <v-card-title>Veículos</v-card-title>
            <v-card-subtitle class="text-h3 primary--text">
              {{ vehicles }}
            </v-card-subtitle>
            <v-card-actions>
              <v-btn color="primary" text @click="$router.push('/vehicles')"
                >Ver Mais</v-btn
              >
            </v-card-actions>
          </v-card>
        </v-col>
        <v-col>
          <v-card elevation="2" class="">
            <v-card-title>Vendas</v-card-title>
            <v-card-subtitle class="text-h3 success--text">
              {{ sales }}
            </v-card-subtitle>
            <v-card-actions>
              <v-btn color="success" text @click="$router.push('/sales')"
                >Ver mais</v-btn
              >
            </v-card-actions>
          </v-card>
        </v-col>
        <v-col>
          <v-card elevation="2" class="">
            <v-card-title>Vendedores</v-card-title>
            <v-card-subtitle class="text-h3 info--text">
              {{ sellers }}
            </v-card-subtitle>
            <v-card-actions>
              <v-btn color="info" text @click="$router.push('/sellers')"
                >Ver mais</v-btn
              >
            </v-card-actions>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </main>
</template>

<script>
import { getHomeInformation } from '../services/api.js';
export default {
  name: 'HomeView',
  data() {
    return {
      vehicles: 0,
      sales: 0,
      sellers: 0
    };
  },
  async created() {
    try {
      const information = await getHomeInformation();
      this.vehicles = information.vehicles;
      this.sales = information.sales;
      this.sellers = information.sellers;
    } catch (e) {
      alert(e);
    }
  }
};
</script>
