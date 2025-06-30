import React, { useState, useEffect } from 'react';
import { Product } from '../types/Product';
import ProductForm from './ProductForm';

const ProductList: React.FC = () => {
    const [products, setProducts] = useState<Product[]>([]);
    const [selectedProduct, setSelectedProduct] = useState<Product | null>(null);
    const [isFormOpen, setIsFormOpen] = useState(false);

    // Simulated API calls (replace with actual API calls)
    const fetchProducts = () => {
        // For now, using the sample data
        const sampleProducts: Product[] = [
            { id: 1, name: 'Laptop', description: 'High-performance laptop', price: 999.99, stock: 50 },
            { id: 2, name: 'Smartphone', description: 'Latest smartphone model', price: 599.99, stock: 100 },
            { id: 3, name: 'Headphones', description: 'Wireless noise-canceling headphones', price: 199.99, stock: 75 }
        ];
        setProducts(sampleProducts);
    };

    useEffect(() => {
        fetchProducts();
    }, []);

    const handleAddProduct = (productData: Omit<Product, 'id'>) => {
        const newProduct: Product = {
            ...productData,
            id: Math.max(...products.map(p => p.id), 0) + 1
        };
        setProducts([...products, newProduct]);
        setIsFormOpen(false);
    };

    const handleUpdateProduct = (productData: Omit<Product, 'id'>) => {
        if (!selectedProduct) return;
        
        const updatedProduct: Product = {
            ...productData,
            id: selectedProduct.id
        };
        
        setProducts(products.map(p => 
            p.id === selectedProduct.id ? updatedProduct : p
        ));
        
        setSelectedProduct(null);
        setIsFormOpen(false);
    };

    const handleDeleteProduct = (id: number) => {
        if (window.confirm('Are you sure you want to delete this product?')) {
            setProducts(products.filter(p => p.id !== id));
        }
    };

    const handleEditClick = (product: Product) => {
        setSelectedProduct(product);
        setIsFormOpen(true);
    };

    return (
        <div className="product-list-container">
            <div className="header">
                <h1>Product Catalog</h1>
                <button 
                    className="btn-add" 
                    onClick={() => {
                        setSelectedProduct(null);
                        setIsFormOpen(true);
                    }}
                >
                    Add New Product
                </button>
            </div>

            {isFormOpen && (
                <div className="modal">
                    <div className="modal-content">
                        <h2>{selectedProduct ? 'Edit Product' : 'Add New Product'}</h2>
                        <ProductForm
                            product={selectedProduct || undefined}
                            onSubmit={selectedProduct ? handleUpdateProduct : handleAddProduct}
                            onCancel={() => {
                                setIsFormOpen(false);
                                setSelectedProduct(null);
                            }}
                        />
                    </div>
                </div>
            )}

            <div className="product-grid">
                {products.map(product => (
                    <div key={product.id} className="product-card">
                        <h3>{product.name}</h3>
                        <p>{product.description}</p>
                        <p className="price">${product.price.toFixed(2)}</p>
                        <p className="stock">Stock: {product.stock}</p>
                        <div className="card-actions">
                            <button 
                                className="btn-edit"
                                onClick={() => handleEditClick(product)}
                            >
                                Edit
                            </button>
                            <button 
                                className="btn-delete"
                                onClick={() => handleDeleteProduct(product.id)}
                            >
                                Delete
                            </button>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default ProductList; 