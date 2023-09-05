<template>
  <main>
    <h2>Vendas</h2>
    <v-row>
      <v-col cols="9" class="mx-auto">
        <v-data-table
          class="mt-10 elevation-1"
          :headers="headers"
          :items="sellers"
          item-key="id"
        >
          <template v-slot:top>
            <v-toolbar flat>
              <v-toolbar-title>Vendedores Cadastrador</v-toolbar-title>
              <v-divider class="mx-4" inset vertical></v-divider>
              <v-spacer></v-spacer>
              <v-dialog v-model="dialog" max-width="850px">
                <template v-slot:activator="{ on, attrs }">
                  <v-btn
                    color="primary"
                    dark
                    class="mb-2"
                    v-bind="attrs"
                    v-on="on"
                  >
                    Novo Vendedor
                  </v-btn>
                </template>
                <v-card>
                  <v-card-title>
                    <span class="text-h5">{{ formTitle }}</span>
                  </v-card-title>

                  <v-card-text>
                    <v-container>
                      <v-row>
                        <h4>Informações Gerais</h4>
                      </v-row>
                      <v-row>
                        <v-col cols="12" sm="6" md="4">
                          <v-text-field
                            v-model="editedSeller.name"
                            label="Nome"
                          ></v-text-field>
                        </v-col>
                        <v-col cols="12" sm="6" md="4">
                          <v-text-field
                            type="email"
                            v-model="editedSeller.email"
                            label="Email"
                          ></v-text-field>
                        </v-col>
                        <v-col cols="12" sm="6" md="4">
                          <v-select
                            v-model="editedSeller.status"
                            :items="['ACTIVE', 'INACTIVE']"
                            label="Status"
                          ></v-select>
                        </v-col>
                        <v-col cols="12" sm="6" md="4">
                          <v-text-field
                            type="date"
                            v-model="editedSeller.joinDate"
                            label="Data de Admissão"
                          ></v-text-field>
                        </v-col>
                      </v-row>
                    </v-container>
                  </v-card-text>

                  <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="blue darken-1" text @click="close">
                      Cancelar
                    </v-btn>
                    <v-btn color="blue darken-1" text @click="save">
                      Salvar
                    </v-btn>
                  </v-card-actions>
                </v-card>
              </v-dialog>
            </v-toolbar>
          </template>
          <template v-slot:item.actions="{ item }">
            <v-icon small class="mr-2" @click="editSeller(item)">
              mdi-pencil
            </v-icon>
          </template>
        </v-data-table>
      </v-col>
    </v-row>
  </main>
</template>

<script>
import { getSellers, createSeller, saveSeller } from '../services/api.js';
export default {
  name: 'SellersView',
  data() {
    return {
      sellers: [],
      headers: [
        {
          text: 'Nome',
          value: 'name'
        },
        {
          text: 'Email',
          value: 'email'
        },
        {
          text: 'Status',
          value: 'status'
        },
        {
          text: 'Data de Admissão',
          value: 'joinDate'
        },
        {
          text: 'Ações',
          value: 'actions',
          sortable: false
        }
      ],
      dialog: false,
      dialogDelete: false,
      editedIndex: -1,
      editedSeller: {
        id: '',
        name: '',
        email: '',
        status: '',
        joinDate: new Date().toString(),
        active: false
      },
      defaultSeller: {
        id: '',
        name: '',
        email: '',
        status: '',
        joinDate: new Date().toString(),
        active: false
      }
    };
  },
  async created() {
    try {
      const sellers = await getSellers();
      this.sellers = sellers;
    } catch (e) {
      alert(e);
    }
  },
  computed: {
    formTitle() {
      return this.editedIndex === -1 ? 'Novo Vendedor' : 'Editar Vendedor';
    }
  },
  watch: {
    dialog(val) {
      val || this.close();
    }
  },
  methods: {
    editSeller(seller) {
      this.editedIndex = this.sellers.indexOf(seller);
      this.editedSeller = Object.assign({}, seller);
      this.dialog = true;
    },

    close() {
      this.dialog = false;
      this.$nextTick(() => {
        this.editedSeller = Object.assign({}, this.defaultSeller);
        this.editedIndex = -1;
      });
    },

    async save() {
      try {
        if (this.editedIndex > -1) {
          console.log(this.editedSeller);
          await saveSeller(this.editedSeller);
          Object.assign(this.sellers[this.editedIndex], this.editedSeller);
        } else {
          await createSeller(this.editedSeller);
          this.sellers.push(this.editedSeller);
        }
        this.close();
      } catch (e) {
        alert(e);
      }
    }
  }
};
</script>
