import networkx as nx
import matplotlib.pyplot as plt

def create_er_diagram():
    # Crear un grafo
    G = nx.Graph()

    # Agregar nodos para las entidades con sus atributos
    G.add_node('Paciente', shape='box', label='Paciente\n--------\n+ nombre\n+ DNI\n+ fecha de nacimiento')
    G.add_node('Médico', shape='box', label='Médico\n--------\n+ nombre\n+ especialidad\n+ número de licencia')
    G.add_node('Visita', shape='box', label='Visita\n--------\n+ fecha\n+ hora')
    G.add_node('Diagnóstico', shape='box', label='Diagnóstico\n--------\n+ enfermedad\n+ observaciones')
    G.add_node('Análisis', shape='box', label='Análisis\n--------\n+ tipo\n+ resultado')
    G.add_node('Historial', shape='box', label='Historial\n--------\n+ número de historial\n+ fecha de creación')

    # Agregar relaciones entre las entidades
    G.add_edge('Paciente', 'Visita', label='realiza')
    G.add_edge('Médico', 'Visita', label='atiende')
    G.add_edge('Visita', 'Diagnóstico', label='tiene')
    G.add_edge('Paciente', 'Historial', label='posee')
    G.add_edge('Historial', 'Análisis', label='registra')

    # Dibujar el grafo
    pos = nx.spring_layout(G)
    nx.draw(G, pos, with_labels=True, node_size=5000, node_color='skyblue', font_size=12, width=2, edge_color='gray')
    
    edge_labels = {(u, v): G[u][v]['label'] for u, v in G.edges()}
    nx.draw_networkx_edge_labels(G, pos, edge_labels=edge_labels, font_size=12)

    plt.title("Esquema de Entidad-Relación con Atributos")
    plt.show()

if __name__ == "__main__":
    create_er_diagram()
