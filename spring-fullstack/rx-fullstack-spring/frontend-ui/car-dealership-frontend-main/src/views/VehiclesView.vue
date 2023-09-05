<template>
  <main>
    <h2>Veículos</h2>
    <v-row>
      <v-col cols="9" class="mx-auto">
        <v-data-table
          class="mt-10 elevation-1"
          :headers="headers"
          :items="vehicles"
          item-key="id"
        >
          <template v-slot:top>
            <v-toolbar flat>
              <v-toolbar-title>Veículos Cadastrados</v-toolbar-title>
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
                    Novo Veículo
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
                            v-model="editedVehicle.name"
                            label="Nome"
                          ></v-text-field>
                        </v-col>
                        <v-col cols="12" sm="6" md="4">
                          <v-text-field
                            v-model="editedVehicle.brand"
                            label="Marca"
                          ></v-text-field>
                        </v-col>
                        <v-col cols="12" sm="6" md="4">
                          <v-select
                            v-model="editedVehicle.status"
                            :items="[
                              { text: 'Ativo', value: 'ACTIVE' },
                              { text: 'Vendido', value: 'SOLD' }
                            ]"
                            label="Status"
                          ></v-select>
                        </v-col>
                        <v-col cols="12" sm="6" md="4">
                          <v-select
                            :items="[
                              { text: 'Branco', value: 'WHITE' },
                              { text: 'Cinza', value: 'GRAY' },
                              { text: 'Preto', value: 'BLACK' },
                              { text: 'Vermelho', value: 'RED' }
                            ]"
                            v-model="editedVehicle.color"
                            label="Cor"
                          >
                          </v-select>
                        </v-col>
                        <v-col cols="12" sm="6" md="4">
                          <v-select
                            :items="['HATCH', 'SEDAN', 'SUV']"
                            v-model="editedVehicle.type"
                            label="Tipo"
                          >
                          </v-select>
                        </v-col>
                        <v-col cols="12" sm="6" md="4">
                          <v-text-field
                            v-model="editedVehicle.chassis"
                            label="Chassis"
                          ></v-text-field>
                        </v-col>
                        <v-col cols="12" sm="6" md="4">
                          <v-text-field
                            type="number"
                            v-model.number="editedVehicle.mileage"
                            label="Quilometragem"
                          ></v-text-field>
                        </v-col>
                        <v-col cols="12" sm="6" md="4">
                          <v-text-field
                            type="number"
                            v-model.number="editedVehicle.releaseYear"
                            label="Ano de Lançamento"
                          ></v-text-field>
                        </v-col>
                      </v-row>
                      <v-row>
                        <h4>Aquisição do Veículo</h4>
                      </v-row>
                      <v-row>
                        <v-col cols="12" sm="6" md="4">
                          <v-text-field
                            type="date"
                            v-model="editedVehicle.acquisition.date"
                            label="Data de Aquisição"
                          ></v-text-field>
                        </v-col>
                        <v-col cols="12" sm="6" md="4">
                          <v-select
                            :items="[
                              {
                                text: 'Particular',
                                value: 'PRIVATE'
                              },
                              {
                                text: 'Concessionária',
                                value: 'VENDOR'
                              }
                            ]"
                            label="Fonte de Aquisição"
                            v-model="editedVehicle.acquisition.source"
                          >
                          </v-select>
                        </v-col>
                        <v-col cols="12" sm="6" md="4">
                          <v-text-field
                            type="number"
                            v-model.number="editedVehicle.acquisition.price"
                            label="Preço de Aquisição"
                          ></v-text-field>
                        </v-col>
                      </v-row>
                      <v-row>
                        <h4>Fotos</h4>
                      </v-row>
                      <v-row v-for="(_, i) in editedVehicle.photos" :key="i">
                        <v-col cols="12" sm="6">
                          <v-text-field
                            v-model="editedVehicle.photos[i].url"
                            label="URL"
                          ></v-text-field>
                        </v-col>
                        <v-col cols="12" sm="5">
                          <v-text-field
                            v-model="editedVehicle.photos[i].description"
                            label="Descrição"
                          ></v-text-field>
                        </v-col>
                        <v-col cols="1" class="align-center">
                          <v-icon small @click="deletePhoto(i)">
                            mdi-delete
                          </v-icon>
                        </v-col>
                      </v-row>
                      <v-row>
                        <v-btn @click="addPhoto">Adicionar Foto</v-btn>
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
            <v-icon small class="mr-2" @click="editVehicle(item)">
              mdi-pencil
            </v-icon>
          </template>
        </v-data-table>
      </v-col>
    </v-row>
  </main>
</template>

<script>
import { getVehicles, createVehicle, saveVehicle } from '../services/api.js';
export default {
  name: 'VehiclesView',
  data() {
    return {
      vehicles: [],
      headers: [
        {
          text: 'Nome',
          value: 'name'
        },
        {
          text: 'Marca',
          value: 'brand'
        },
        {
          text: 'Status',
          value: 'status'
        },
        {
          text: 'Cor',
          value: 'color'
        },
        {
          text: 'Preço de Aquisição',
          value: 'acquisition.price'
        },
        {
          text: 'Ações',
          value: 'actions',
          sortable: false
        }
      ],
      dialog: false,
      editedIndex: -1,
      editedVehicle: {
        id: 0,
        name: '',
        brand: '',
        color: '',
        status: '',
        type: '',
        chassis: '',
        mileage: 0,
        releaseYear: 0,
        acquisition: {
          date: '',
          price: 0,
          source: ''
        },
        photos: [
          {
            description: '',
            url: ''
          }
        ],
        sold: false
      },
      defaultVehicle: {
        id: 0,
        name: '',
        brand: '',
        color: '',
        status: '',
        type: '',
        chassis: '',
        mileage: 0,
        releaseYear: 0,
        acquisition: {
          date: '',
          price: 0,
          source: ''
        },
        photos: [
          {
            description: '',
            url: ''
          }
        ],
        sold: false
      }
    };
  },
  async created() {
    try {
      const vehicles = await getVehicles();
      this.vehicles = vehicles;
    } catch (e) {
      alert(e);
    }
  },
  computed: {
    formTitle() {
      return this.editedIndex === -1 ? 'Novo Veículo' : 'Editar Vehicle';
    }
  },
  watch: {
    dialog(val) {
      val || this.close();
    }
  },
  methods: {
    editVehicle(vehicle) {
      this.editedIndex = this.vehicles.indexOf(vehicle);
      this.editedVehicle = Object.assign({}, vehicle);
      this.dialog = true;
    },

    close() {
      this.dialog = false;
      this.$nextTick(() => {
        this.editedVehicle = Object.assign({}, this.defaultVehicle);
        this.editedIndex = -1;
      });
    },

    async save() {
      try {
        if (this.editedIndex > -1) {
          await saveVehicle(this.editedVehicle);
          Object.assign(this.vehicles[this.editedIndex], this.editedVehicle);
        } else {
          await createVehicle(this.editedVehicle);
          this.vehicles.push(this.editedVehicle);
        }
        this.close();
      } catch (e) {
        alert(e);
      }
    },

    addPhoto() {
      if (!this.editedVehicle.photos) {
        this.editedVehicle.photos = [];
      }
      this.editedVehicle.photos.push({ url: '', description: '' });
    },

    deletePhoto(i) {
      this.editedVehicle.photos.splice(i, 1);
    }
  }
};
</script>
