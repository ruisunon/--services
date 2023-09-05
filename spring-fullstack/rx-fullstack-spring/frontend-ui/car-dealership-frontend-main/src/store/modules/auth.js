import { login } from '@/services/api';

const state = {
  token: null
};

const getters = {
  isAuthenticated: (state) => state.token != null
};

const actions = {
  async Login({ commit }, { email, password }) {
    console.log('here2');
    const token = await login({ email, password });
    commit('login', token);
  },
  async Logout({ commit }) {
    commit('logout');
  }
};

const mutations = {
  login(state, token) {
    state.token = token;
  },
  logout(state) {
    state.token = null;
  }
};

export default {
  state,
  getters,
  actions,
  mutations
};
